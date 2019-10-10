package vineeth.test.com.testapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.data_base.DataBaseHelper;
import vineeth.test.com.testapp.room_db_ex.UserActivity;

public class FlashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        //check db updates
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.getWritableDatabase();
        startActivity(new Intent(this, UserActivity.class));
    }


}
