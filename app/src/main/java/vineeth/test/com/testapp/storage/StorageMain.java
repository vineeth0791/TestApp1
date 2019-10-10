package vineeth.test.com.testapp.storage;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import vineeth.test.com.testapp.R;

public class StorageMain extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_main);
        saveInternal();
        readInternal();
        getExternalDirs();
    }

    private void saveInternal()
    {
        Button writeInternalButton = findViewById(R.id.write_internal);
        writeInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = findViewById(R.id.editText);
                String text  =editText.getText().toString();
                EditText fileNameET  = findViewById(R.id.file_name);
                String fileName = fileNameET.getText().toString();

                saveUsingOpenFileOutPut(text,fileName);
             }
        });
    }

    private void saveUsingOpenFileOutPut(String text,String fileName)
    {
        try {

            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }catch(FileNotFoundException ex)
        {
            Toast.makeText(StorageMain.this,"File Not found Exception ",Toast.LENGTH_LONG).show();
        }catch(IOException e)
        {
            Toast.makeText(StorageMain.this,"IO Exception "+e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }
    private void readInternal()
    {
        Button readInternalButton = (Button)findViewById(R.id.read_internal);
        readInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fileNameET  = findViewById(R.id.file_name);
                String fileName = fileNameET.getText().toString();
                readInternalFile(fileName);
            }
        });
    }

    private void readInternalFile(String fileName)
    {
        try {
            BufferedReader bis = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = bis.readLine()) != null) {
                sb.append(line + "\n");
            }

            TextView textView = findViewById(R.id.read_text);
            textView.setText(sb.toString());
        }catch(FileNotFoundException ex)
        {
            Toast.makeText(StorageMain.this,"File not found",Toast.LENGTH_SHORT).show();
        }catch(IOException ex)
        {
            Toast.makeText(StorageMain.this,"IO Exception "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void getExternalDirs()
    {
        Button getDirs = findViewById(R.id.external_dirs);
        getDirs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("External File","External file is "+Environment.getExternalStorageDirectory().getAbsolutePath());
                File[] files = ContextCompat.getExternalFilesDirs(StorageMain.this, Environment.DIRECTORY_DOWNLOADS);
                for(File file:files)
                {
                    Log.d("External File","File is "+file.getAbsolutePath());
                }
            }
        });

    }

}
