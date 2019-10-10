package vineeth.test.com.testapp.camera_api;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vineeth.test.com.testapp.R;

public class Camera2ApiExample extends AppCompatActivity {
    private Context context;
    private final static int PERMISSION =1;
    private final static int CODE_DRAW_OVER_OTHER_APP_PERMISSION =2;
    private TextureView textureView;
    private CameraDevice mCameraDevice;
    private Size imageDimension;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;

    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;



    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_two_api);
        context = Camera2ApiExample.this;

        //check for permissions
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
           ActivityCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            //check for permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION);

        }


        //startBackGroundService();
        prepareTextureView();

    }

    public void onRequestPermissionsResult(int request,String[] permissions,int[] grantResult)
    {
        if(request==PERMISSION) {

            if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                prepareTextureView();
               //startBackGroundService();
            }
        }
    }

    private void prepareTextureView()
    {
        /*textureView = findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(surfaceTextureListener);

        ((Button)findViewById(R.id.capture_image_c2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });*/
        startBackGroundService();
    }

    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            Log.d("Camera2","Inside on surfaceTexture available "+i+";"+i1);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
            Log.d("Camera2","Inside surface texture size changed "+i+";"+i1);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Log.d("Camera2","Inside on Surface texture destroyed");
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            Log.d("Camera2","Inside on surface texture updated");
        }
    };

    private void openCamera()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try
            {
                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                String[] cameraIds = cameraManager.getCameraIdList();
                Log.d("Camera2","Inside Total number of cameras "+cameraIds.length);
                for(String cameraId:cameraIds)
                {
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                    if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
                    {
                        try
                        {
                            StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                            if(map!=null)
                            {
                                imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
                                cameraManager.openCamera(cameraId,cameraStateCallBacks,null);
                            }

                        }catch(SecurityException ex)
                        {
                            ex.printStackTrace();
                            Toast.makeText(context,"Camera permission is not granted",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }
                }

            }catch (CameraAccessException ex)
            {
                Toast.makeText(context,"Camera is not available",Toast.LENGTH_SHORT).show();
            }

        }else
        {
            Toast.makeText(context,"Camera functionality does not support",Toast.LENGTH_SHORT).show();
        }
    }


    private final CameraDevice.StateCallback cameraStateCallBacks = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            Log.d("Camera2","Inside on camera opened");
            mCameraDevice = cameraDevice;
            createPreview();
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
          Log.d("Camera2","Inside on camera disconnected");
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
            Log.d("Camera2","Inside camera device on error");
            Toast.makeText(context,"Error in starting camera",Toast.LENGTH_SHORT).show();
        }
    };



    //create preview
    private void createPreview() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            if (surfaceTexture != null) {
                surfaceTexture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
                Surface surface = new Surface(surfaceTexture);
                try {
                captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                captureRequestBuilder.addTarget(surface);

                    mCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                          Log.d("Camera2","inside create camera capture session on configured");
                            mCameraCaptureSession = cameraCaptureSession;

                            updatePreview();
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            Toast.makeText(context,"Unable to create capture session onConfigureFailed",Toast.LENGTH_SHORT).show();
                        }
                    }, null);
                } catch (CameraAccessException ex) {
                    ex.printStackTrace();
                    Toast.makeText(context,"Unable to create capture session ",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updatePreview()
    {
       if(mCameraDevice!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
       {
           try {
               captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
               captureRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE,
                       CameraMetadata.STATISTICS_FACE_DETECT_MODE_FULL);
               setAutoFlash(captureRequestBuilder);

               mCameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                   @Override
                   public void onCaptureStarted(CameraCaptureSession session,  CaptureRequest request, long timestamp, long frameNumber) {
                       super.onCaptureStarted(session, request, timestamp, frameNumber);
                       Log.d("Camera2","Inside on capture started");
                   }
                   @Override
                   public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                       //process(partialResult);
                       Log.d("Camera2","Inside on capture progressed");
                   }

                   @Override
                   public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                       //process(result);
                       Log.d("Camera2","Inside on capture completed");
                       process(result);
                   }

                   private void process(CaptureResult result) {
                       Integer mode = result.get(CaptureResult.STATISTICS_FACE_DETECT_MODE);
                       Face[] faces = result.get(CaptureResult.STATISTICS_FACES);
                       if (faces != null && mode != null) {
                           Log.d("tag", "faces : " + faces.length + " , mode : " + mode);
                       }
                   }

               }, mBackgroundHandler);
           }catch(CameraAccessException ex) {
               ex.printStackTrace();
               Toast.makeText(context,"Unable to access camera",Toast.LENGTH_LONG).show();
           }
       }
    }

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != mCameraDevice) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                mCameraDevice.close();
            }

            mCameraDevice = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

      /*  startBackgroundThread();
       if(textureView!=null) {
           if (textureView.isAvailable()) {
               openCamera();
           } else {
               textureView.setSurfaceTextureListener(surfaceTextureListener);
           }
       }*/
    }


    public void onPause()
    {
        super.onPause();
       /* closeCamera();
        stopBackgroundThread();*/
    }

    private void takePicture()
    {
        if (mCameraDevice != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        try {

                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

                //get available size
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(mCameraDevice.getId());
                Size size = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG)[0];
                int width=640,height=480;
                if(size!=null)
                {
                    width = size.getWidth();
                    height = size.getHeight();
                }

                //create Image Reader
            ImageReader imageReader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface>  surfaces = new ArrayList<>(2);
            surfaces.add(imageReader.getSurface());
            surfaces.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
            int orientation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,orientation);
            final File file = new File(getFilesDir()+"/pic.jpg");
            ImageReader.OnImageAvailableListener imageAvailableListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                    Image image = null;
                    try {
                        image = imageReader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            };

            imageReader.setOnImageAvailableListener(imageAvailableListener,mBackgroundHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(context, "Saved:" + file, Toast.LENGTH_SHORT).show();
                    process(result);
                    createPreview();
                }

                private void process(CaptureResult result) {
                    Integer mode = result.get(CaptureResult.STATISTICS_FACE_DETECT_MODE);
                    Face[] faces = result.get(CaptureResult.STATISTICS_FACES);
                    if (faces != null && mode != null) {
                        Log.d("tag", "faces : " + faces.length + " , mode : " + mode);
                    }
                }
            };

            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured( CameraCaptureSession cameraCaptureSession) {
                    try {
                        cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    }catch (Exception e)
                    {

                    }
                }

                @Override
                public void onConfigureFailed( CameraCaptureSession cameraCaptureSession) {

                }
            },mBackgroundHandler);
        }catch (CameraAccessException ex)
        {
            ex.printStackTrace();
            Toast.makeText(context, "Camera error ", Toast.LENGTH_SHORT).show();
        }
        } else {
            Toast.makeText(context, "Camera device not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAutoFlash(CaptureRequest.Builder requestBuilder)
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            try {
                boolean available = cameraManager.getCameraCharacteristics(mCameraDevice.getId()).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (available)
                {
                    requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                }
            }catch(CameraAccessException ex)
            {

            }
        }
    }

    private void startBackGroundService()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            startService(new Intent(context,Camera2ApiService.class));
        }

    }

    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case CODE_DRAW_OVER_OTHER_APP_PERMISSION:
                    startBackGroundService();
                    break;
            }
        }
    }


}
