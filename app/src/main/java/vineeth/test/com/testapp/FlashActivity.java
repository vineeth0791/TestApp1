package vineeth.test.com.testapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.data_base.RoomDBHelper;
import vineeth.test.com.testapp.room_db_ex.UserActivity;

public class FlashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RoomDBHelper.getInstance(this);

        startActivity(new Intent(this, MainFragment.class));
    }


}
