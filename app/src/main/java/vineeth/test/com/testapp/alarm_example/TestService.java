package vineeth.test.com.testapp.alarm_example;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vineeth.test.com.testapp.R;

public class TestService extends Service {

    public void onCreate()
    {
        super.onCreate();
        setForegroundNotification();
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int id)
    {
        //set notification
        setNotification();

        stopSelf();

        return START_NOT_STICKY;
    }

    private void setForegroundNotification()
    {
        Notification notification = getNotification();
        startForeground(1,notification);
    }

    private Notification getNotification()
    {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("alarm","Alarm", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Alarm Service");
            manager.createNotificationChannel(notificationChannel);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        return new NotificationCompat.Builder(TestService.this,"alarm")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Alarm service")
                .setContentText(sdf.format(calendar.getTime()))
                .setOngoing(false)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    private void setNotification()
    {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("alarm","Alarm", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Alarm Service");
            manager.createNotificationChannel(notificationChannel);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        Notification notification = new NotificationCompat.Builder(TestService.this,"alarm")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Alarm service")
                .setContentText(sdf.format(calendar.getTime()))
                .setOngoing(false)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        manager.notify(123,notification);
    }


}
