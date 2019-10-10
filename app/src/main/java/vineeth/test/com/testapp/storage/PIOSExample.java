package vineeth.test.com.testapp.storage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;

public class PIOSExample extends AppCompatActivity {
    private Context context;
    private final static String TAG = "PIOSExample";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = PIOSExample.this;

        //create temp file
        String fileName = "Record_"+System.currentTimeMillis();
        try {
            File recordFile = File.createTempFile(fileName,".mp3",getFilesDir());
            PipedOutputStream inputStream = new PipedOutputStream();
            //AudioRecord
            Log.d(TAG,"record file has been created "+recordFile.getAbsolutePath());
        }catch(IOException ex)
        {
            ex.printStackTrace();
            Toast.makeText(context,"Unable to create file",Toast.LENGTH_SHORT).show();
        }
    }
}
