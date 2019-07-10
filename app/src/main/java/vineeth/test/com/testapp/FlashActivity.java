package vineeth.test.com.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import vineeth.test.com.testapp.data_base.DataBaseHelper;

public class FlashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //check db updates
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.getWritableDatabase();

        startActivity(new Intent(this, MainActivity.class));
    }
}
