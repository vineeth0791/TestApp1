package vineeth.test.com.testapp.camera_api;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import android.util.Size;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import vineeth.test.com.testapp.R;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Camera2ApiService extends Service {
    private final static String TAG = "Camera2ApiService";
    private View chatHeadView;
    private WindowManager windowManager;
    private TextureView textureView;
    private Context context;
    private CameraDevice mCameraDevice;
    private Size imageDimension;
    private static final int DEFAULT_PREVIEW_WIDTH = 100;
    private static final int DEFAULT_PREVIEW_HEIGHT = 100;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private TextView facesTextView;
    private int prevFaceCount=0;
    private static boolean canCaptureAndProcess = true;
    private static long lastFaceDetectionTime=0;
    private Handler faceRecgCBHandler;

    public Camera2ApiService() {
        super();

    }

    public void onCreate()
    {
        super.onCreate();
        context = Camera2ApiService.this;
        startBackgroundThread();
        startFaceRecgCBHandler();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int service) {
        Log.d(TAG, "Inside Camera2ApiService on start command");
        displayChatHead();
        return START_STICKY;
    }

    private void displayChatHead() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // textureView = chatHeadView.findViewById(R.id.texture_view);

        textureView = new TextureView(context);
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        Log.d(TAG, "Inside set surface Texture listeners");

        WindowManager.LayoutParams params ;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    PixelFormat.TRANSLUCENT);
        }else
        {
           params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 100;

        /*params.width = DEFAULT_PREVIEW_WIDTH;
        params.height = DEFAULT_PREVIEW_HEIGHT;*/

        windowManager.addView(textureView, params);

        WindowManager.LayoutParams chatHeadParams;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            chatHeadParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    PixelFormat.TRANSLUCENT);
        }else
        {
            chatHeadParams  = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        }

        chatHeadParams.gravity = Gravity.RIGHT | Gravity.TOP;
        chatHeadParams.x = 0;
        chatHeadParams.y = 100;

        if (chatHeadView == null) {
            chatHeadView = LayoutInflater.from(this).inflate(R.layout.chat_head, null);
            facesTextView = chatHeadView.findViewById(R.id.faces);
            ImageView closeButton = (ImageView) chatHeadView.findViewById(R.id.close_btn);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //close the service and remove the chat head from the window
                    stopSelf();
                }
            });

            Button captureImage = (Button)chatHeadView.findViewById(R.id.capture_image);
            captureImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePicture();
                }
            });
        }


        windowManager.addView(chatHeadView, chatHeadParams);

    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside on destroy");
        if (chatHeadView != null) {
            windowManager.removeView(chatHeadView);
            chatHeadView = null;
        }

        if (textureView != null) {
            windowManager.removeView(textureView);
            textureView = null;
        }

        stopBackgroundThread();

        closeCamera();

        stopFaceRecgCBHandler();
    }

    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            Log.d(TAG, "Inside on surface Texture available i" + i + "i1" + i1);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
            Log.d(TAG, "Inside on surface Texture size changed i" + i + "i1" + i1);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Log.d(TAG, "Inside on surface Texture destroyed ");
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
           // Log.d(TAG, "Inside on surface Texture updated ");
        }
    };


    private void openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                try {
                    CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                    String[] cameraIds = cameraManager.getCameraIdList();
                    for (String cameraId : cameraIds) {
                        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                        if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                            StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                            if (map != null) {
                                imageDimension = map.getOutputSizes(ImageFormat.JPEG)[0];
                            }
                            cameraManager.openCamera(cameraId, cameraDeviceStateCallBacks, null);
                            break;
                        }
                    }
                } catch (CameraAccessException ex) {
                    Toast.makeText(context, "Unable to access the camera ", Toast.LENGTH_SHORT).show();
                    stopSelf();
                } catch (SecurityException ex) {
                    Toast.makeText(context, "permission denied to access camera", Toast.LENGTH_SHORT).show();
                    stopSelf();
                }

            } else {
                Toast.makeText(context, "No cameras found", Toast.LENGTH_SHORT).show();
                stopSelf();
            }
        } else {
            Toast.makeText(context, "This feature is not supported", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    @TargetApi(21)
    private CameraDevice.StateCallback cameraDeviceStateCallBacks = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            Log.d(TAG, "Inside camera device state call backs on opened");
            mCameraDevice = cameraDevice;
            createPreview();
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            Log.d(TAG, "Inside camera device state call backs on disconnected");
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
            Log.d(TAG, "Inside camera device state call backs on error " + i);
            Toast.makeText(context, "Inside on device on error", Toast.LENGTH_SHORT);
            stopSelf();
        }
    };

    @TargetApi(21)
    private void createPreview() {

        if (textureView != null) {
            canCaptureAndProcess = true;

            try {
                SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
                if (imageDimension != null) {
                    surfaceTexture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
                } else {
                    surfaceTexture.setDefaultBufferSize(DEFAULT_PREVIEW_WIDTH, DEFAULT_PREVIEW_HEIGHT);
                }
                Surface surface = new Surface(surfaceTexture);
                captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                captureRequestBuilder.addTarget(surface);


                mCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        Log.d(TAG, "inside on configured capture session ");
                        mCameraCaptureSession = cameraCaptureSession;
                        updatePreview();
                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        Toast.makeText(context, "On configuration failed inside create capture session", Toast.LENGTH_SHORT).show();
                        stopSelf();

                    }
                }, mBackgroundHandler);
            } catch (CameraAccessException ex) {
                ex.printStackTrace();
                Toast.makeText(context, "Unable to access the camera ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "Texture view is null", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    @TargetApi(21)
    private void updatePreview() {
        if(captureRequestBuilder!=null && mCameraCaptureSession!=null) {
            captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
            captureRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, CaptureRequest.STATISTICS_FACE_DETECT_MODE_FULL);
            setAutoFlash(captureRequestBuilder);
            try {
               mCameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                   @Override
                   public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
                       super.onCaptureStarted(session, request, timestamp, frameNumber);
                       Log.d("Camera2", "Inside on capture started");
                   }

                   @Override
                   public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                       //process(partialResult);
                       //Log.d(TAG, "Inside on capture progressed");
                       process(partialResult);
                   }

                   @Override
                   public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                       //process(result);
                       //Log.d(TAG, "Inside on capture completed");
                       process(result);
                   }

                   private void process(CaptureResult result) {
                       Integer mode = result.get(CaptureResult.STATISTICS_FACE_DETECT_MODE);
                       Face[] faces = result.get(CaptureResult.STATISTICS_FACES);
                       if (faces != null && mode != null) {
                           int totalFacesDetected = faces.length;
                           if(prevFaceCount!=totalFacesDetected)
                           {

                               prevFaceCount = totalFacesDetected;

                               displayFaces();
                               if(totalFacesDetected>=1 && canCaptureAndProcess) {
                                   lastFaceDetectionTime = System.currentTimeMillis();
                                   canCaptureAndProcess = false;
                                   captureAndProcessImage();
                               }
                           }

                           Log.d(TAG, "faces : " + totalFacesDetected + " , mode : " + mode);


                       }
                   }

               }, mBackgroundHandler);
           }catch (CameraAccessException ex)
           {
               ex.printStackTrace();
               Toast.makeText(context,"Unable to access camera",Toast.LENGTH_SHORT).show();
               stopSelf();
           }
        }else
        {
            Toast.makeText(context,"Unable to update preview",Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    private void captureAndProcessImage()
    {
        takePicture();
    }

    //start back ground thread to start preview
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());

    }

  @TargetApi(21)
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

    @TargetApi(21)
    protected void stopBackgroundThread(HandlerThread thread,Handler captureThreadHandler) {
        thread.quitSafely();
        captureThreadHandler= null;
        thread = null;

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
                    Toast.makeText(context,"Auto flash on",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(context,"Auto flash not available",Toast.LENGTH_SHORT).show();
                }
            }catch(CameraAccessException ex)
            {
              ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void takePicture()
    {


        ImageReader imageReader;
        try {
        CameraManager manager = (CameraManager)getSystemService(CAMERA_SERVICE);
        CameraCharacteristics cameraCharacteristics=manager.getCameraCharacteristics(mCameraDevice.getId());
        StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if(map!=null)
        {

            imageReader =  ImageReader.newInstance(map.getOutputSizes(ImageFormat.JPEG)[0].getWidth(),map.getOutputSizes(ImageFormat.JPEG)[0].getHeight(),ImageFormat.JPEG,1);
        }else
        {
            imageReader = ImageReader.newInstance(DEFAULT_PREVIEW_WIDTH,DEFAULT_PREVIEW_HEIGHT,ImageFormat.JPEG,1);
        }


            final CaptureRequest.Builder cameraCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            List<Surface> surfaces = new ArrayList<>();
            surfaces.add(imageReader.getSurface());
            surfaces.add(new Surface(textureView.getSurfaceTexture()));
            cameraCaptureRequest.addTarget(imageReader.getSurface());
            cameraCaptureRequest.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
            cameraCaptureRequest.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE,CaptureRequest.STATISTICS_FACE_DETECT_MODE_FULL);
            int orientation = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            Log.d(TAG,"Inside take picture on orientation - "+orientation);
            //cameraCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION,orientation);
            final File file = new File(getFilesDir()+"/pic.jpg");
            ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                 Log.d(TAG,"Inside take picture on image available");
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

                        Log.d(TAG,"Inside take picture on image available saved");
                    }
                }
            };
            imageReader.setOnImageAvailableListener(onImageAvailableListener,mBackgroundHandler);

            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                  try {
                      cameraCaptureSession.capture(cameraCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() {
                          @Override
                          public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                              super.onCaptureCompleted(session, request, result);
                              Log.d(TAG, "Inside take picture on capture completed");
                              Toast.makeText(context, "Saved:", Toast.LENGTH_SHORT).show();
                             processImage();

                          }

                          @Override
                          public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                              //process(partialResult);
                              Log.d(TAG, "Inside take picture on capture progressed");
                             // process(partialResult);
                          }

                          @Override
                          public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
                              super.onCaptureStarted(session, request, timestamp, frameNumber);
                              Log.d("Camera2", "Inside take picture on capture started");
                          }

                      }, mBackgroundHandler);
                  }catch(CameraAccessException ex)
                  {
                      Toast.makeText(context,"Unable to capture please try again",Toast.LENGTH_SHORT).show();
                      createPreview();
                  }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {

                }
            },mBackgroundHandler);
        }catch(CameraAccessException ex)
        {
            Toast.makeText(context,"Unable to access camera",Toast.LENGTH_SHORT).show();
            stopSelf();
        }

    }

    private void restartPreview()
    {
        closeCamera();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                openCamera();
            }
        },2000);

    }

    @TargetApi(21)
    private void closeCamera()
    {
        if(mCameraDevice!=null)
        {
            mCameraDevice.close();
            mCameraDevice=null;
        }

    }

    private void displayFaces()
    {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                facesTextView.setText("Total faces detected "+prevFaceCount);
                Toast.makeText(context,"Total faces detected "+prevFaceCount,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processImage()
    {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                canCaptureAndProcess=true;
                restartPreview();
            }
        },10000);//delay time is 10 seconds
    }

    private Runnable faceRecgCBHandlerClass = new Runnable() {
        @Override
        public void run() {
            if(faceRecgCBHandler!=null)
            {
            long currentTimeInMs = System.currentTimeMillis();
            if(currentTimeInMs-lastFaceDetectionTime >= (60000*15))
            {
                restartPreview();
            }

                faceRecgCBHandler.postDelayed(this,60000*15);
            }
        }
    };

    private void startFaceRecgCBHandler()
    {
        faceRecgCBHandler = new Handler();
        faceRecgCBHandler.postDelayed(faceRecgCBHandlerClass,(60000*15));//every 15 minutes
    }

    private void stopFaceRecgCBHandler()
    {
        try {
            faceRecgCBHandler.removeCallbacks(faceRecgCBHandlerClass);
        }finally {
            faceRecgCBHandler = null;
        }
    }
}

