package vineeth.test.com.testapp.alarm_example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ACTION = "vineeth.test.com.testapp.alarm_example.AlarmReceiver";

    public void onReceive(Context context, Intent intent)
    {
          Intent serviceIntent = new Intent(context,TestService.class);
          ContextCompat.startForegroundService(context,serviceIntent);
    }
}
