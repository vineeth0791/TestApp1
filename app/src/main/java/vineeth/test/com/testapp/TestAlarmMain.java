package vineeth.test.com.testapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vineeth.test.com.testapp.alarm_example.AlarmReceiver;

;

public class TestAlarmMain extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_alarm_main);
        startAlarm();
    }

    private void startAlarm()
    {
       final EditText editText = findViewById(R.id.time);
        Button button = findViewById(R.id.start_alarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {

                    int minutes  = Integer.parseInt(editText.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM");
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE,minutes);

                    Intent intent = new Intent(TestAlarmMain.this, AlarmReceiver.class);
                    intent.setAction(AlarmReceiver.ACTION);

                    AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(TestAlarmMain.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        manager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    }else {
                        manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    }

                    Toast.makeText(TestAlarmMain.this,"Alarm started "+sdf.format(calendar.getTime()),Toast.LENGTH_LONG).show();

                }catch(ClassCastException ex)
                {
                    Toast.makeText(TestAlarmMain.this,"Invalid time",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
