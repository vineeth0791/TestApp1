package vineeth.test.com.testapp.storage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class FOSExample extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        saveTextFile();
    }

    private void saveTextFile()
    {

        String text = "Hi hello";
        String text2 = "\n Hi hello 2";
        try {
            FileOutputStream fos = openFileOutput("fos_example.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.write(text2.getBytes());
            Toast.makeText(this,"File has been saved",Toast.LENGTH_LONG).show();
        }catch (IOException ex)
        {
            ex.printStackTrace();
            Toast.makeText(this,"Unable to save file"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
