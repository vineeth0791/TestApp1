package vineeth.test.com.testapp.storage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;

public class FISExample extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        readFile();
    }

    private void readFile()
    {
        try
        {
            FileInputStream fis = openFileInput("fos_example.txt");
            int i;
            StringBuffer sb = new StringBuffer();
            while((i=fis.read())!=-1)
            {
                sb.append((char)i);
            }

            Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();
        }catch(IOException ex)
        {
            Toast.makeText(this,"Unable to read"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}
