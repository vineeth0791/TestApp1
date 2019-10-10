package vineeth.test.com.testapp.camera_api;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vineeth.test.com.testapp.R;

public class CameraApiExample extends AppCompatActivity {
    private Camera camera;
    private Context context;
    private int cameraId;
    private final static int CAMERA_PERMISSION=1;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_api_example);
        context=CameraApiExample.this;

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

            checkAndStartCamera();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_PERMISSION);
        }
    }

    private void checkAndStartCamera()    {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(context, "No Cameras found", Toast.LENGTH_LONG).show();
            finish();
        } else {
            //check for front camera// get the front camera
            cameraId = getFrontCamera();
            if (cameraId < 0) {
                Toast.makeText(context, "No Front camera found", Toast.LENGTH_LONG).show();
                finish();
            } else {
                //init camera
                camera = Camera.open(cameraId);
                //camera.setFaceDetectionListener(new FaceDetectionListeners());


                //camera.startPreview();
                CameraPreview cameraPreview = new CameraPreview(this, camera);
                FrameLayout preview = findViewById(R.id.camera_preview);
                preview.addView(cameraPreview);

                checkAndSetAutoFocusMode();
                checkAndSetMeteringArea();



                findViewById(R.id.captureFront).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        camera.takePicture(null, null, new CapturePictureCallBacks());
                    }
                });

            }

        }
    }

    public void onRequestPermissionsResult(int request,String[] permissions,int[] grantResult)
    {
        if(request==CAMERA_PERMISSION)
        {
            if(grantResult[0]==PackageManager.PERMISSION_GRANTED)
            {
                checkAndStartCamera();
            }
        }
    }

    private int getFrontCamera()
    {
        for(int i=0;i<Camera.getNumberOfCameras();i++)
        {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i,cameraInfo);
            if(cameraInfo.facing==Camera.CameraInfo.CAMERA_FACING_FRONT)
            {
                return i;
            }
        }

        return -1;
    }

    private class CapturePictureCallBacks implements Camera.PictureCallback
    {
        public void onPictureTaken(byte[] data,Camera camera)
        {
           //save to internal directory
            File dir = getFilesDir();
            dir = new File(dir,"CameraApi");
            if(!dir.exists())
            {
                dir.mkdir();
            }
            String fileName = Calendar.getInstance().getTimeInMillis()+".jpeg";
            try
            {
               // File saveFile = File.createTempFile(fileName,".jpeg",dir);
                //write to save file

                FileOutputStream fos = openFileOutput(fileName,MODE_PRIVATE);
                fos.write(data);
                fos.close();
                Toast.makeText(context,"Image has been captured successfully",Toast.LENGTH_SHORT).show();
            }catch(Exception e)
            {
                Toast.makeText(context,"Unable to save captured image",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void checkAndSetAutoFocusMode()
    {
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
        {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.setParameters(parameters);
            Toast.makeText(context,"AutoFocus On",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(context,"No AutoFocus Mode",Toast.LENGTH_LONG).show();
        }
    }

    //check and set meterting area
    private void checkAndSetMeteringArea()
    {
        Camera.Parameters cp = camera.getParameters();
        if(0<cp.getMaxNumMeteringAreas())
        {
            ArrayList<Camera.Area> meteringAreas = new ArrayList<>();
            Rect rect1 = new Rect(-100, -100, 100, 100);
            meteringAreas.add(new Camera.Area(rect1,600));
            Rect rect2 = new Rect(800,-1000,1000,-800);
            meteringAreas.add(new Camera.Area(rect2,400));
            cp.setMeteringAreas(meteringAreas);
            camera.setParameters(cp);
        }else
        {
            Toast.makeText(context,"Metering feature not available",Toast.LENGTH_SHORT).show();
        }
    }



    public void onPause()
    {
        super.onPause();
        //finish();
    }

    public void onDestroy()
    {
        super.onDestroy();
        if(camera!=null)
        {
           camera.release();
           camera=null;
        }
    }


}
