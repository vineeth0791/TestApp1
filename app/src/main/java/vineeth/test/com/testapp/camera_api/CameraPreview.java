package vineeth.test.com.testapp.camera_api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Context context;
    private int x,y;

    public CameraPreview(Context context, Camera camera)
    {
       super(context);
       surfaceHolder = getHolder();
       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

       this.camera = camera;
       this.context=context;

        camera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                if(faces.length>0)
                {
                    Log.d("FaceDetection", "face detected: "+ faces.length +
                            " Face 1 Location X: " + faces[0].rect.centerX() +
                            "Y: " + faces[0].rect.centerY() );
                    x= faces[0].rect.centerX();
                    y = faces[0].rect.centerY();
                    invalidate();
                }
            }
        });

        surfaceHolder.addCallback(this);
        setWillNotDraw(false);
    }



    public void surfaceCreated(SurfaceHolder holder)
    {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            startFaceDetection();
        }catch(IOException ex)
        {
            ex.printStackTrace();

            Log.d("CameraPreview","Error in setting preview "+ex.getMessage());
            //Toast.makeText(context,"Error in setting preview",Toast.LENGTH_SHORT).show();
        }

    }

    public void surfaceChanged(SurfaceHolder holder,int type,int w,int h)
    {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (surfaceHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

            startFaceDetection();

        } catch (Exception e){
            Log.d("CameraPreview", "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
           Log.d("CameraPreview","Inside surface destroyed");
    }

    public void startFaceDetection()
    {
        Camera.Parameters params = camera.getParameters();
        if(params.getMaxNumDetectedFaces()>0)
        {
            camera.startFaceDetection();
            Toast.makeText(context,"Face detection started",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Face detection is not supported",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
       Log.d("CameraPreview","Inside on draw method x = "+x+",y"+y);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(false);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x, y, 100, paint);
    }

}
