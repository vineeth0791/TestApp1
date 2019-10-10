package vineeth.test.com.testapp.media;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import vineeth.test.com.testapp.R;



public class MediaRecorderActivity extends AppCompatActivity {
    Chronometer chronometer;
    MediaRecorder mediaRecorder;
    private final static int PERMISSION=1;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_recorder);
        chronometer=findViewById(R.id.chrono_meter);


      if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)
      {
          startRecording();
          stopRecording();
      }else
      {
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},PERMISSION);
      }

    }

    private void startRecording()
    {
        Button startRecording = findViewById(R.id.record);
        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    File saveFile = getSaveFile();
                    Log.d("MediaRecord", "Save file location is " + saveFile.getAbsolutePath());
                    mediaRecorder.setOutputFile(saveFile.getAbsolutePath());
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }catch(IOException exception)
                {
                    Toast.makeText(MediaRecorderActivity.this,"IO Exception "+exception.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void stopRecording()
    {
        Button stopRecording = findViewById(R.id.stop_recording);
        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                chronometer.stop();

            }
        });
    }

    private File getSaveFile()
    {
        File dir;
      if(Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED)
      {
          dir=Environment.getExternalStorageDirectory();
      }else
      {
          dir = getFilesDir();
      }

      dir = new File(dir+"/Recorder/");
      if(!dir.exists())
      {
          dir.mkdirs();
      }

     return new File(dir, SystemClock.currentThreadTimeMillis()+".mp3");
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult)
    {
         if(requestCode==PERMISSION)
         {
             if(grantResult[0]==PackageManager.PERMISSION_GRANTED)
             {
                 startRecording();
                 stopRecording();
             }
         }
    }
}
