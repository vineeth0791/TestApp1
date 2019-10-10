package vineeth.test.com.testapp.speech_to_text;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SpeechRecognizerMainActivity  extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
       }
        startRecgService();

    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            startRecgService();
        }
    }

    private void startRecgService()
    {
           startService(new Intent(this,SpeechRecgService.class));
    }
}
