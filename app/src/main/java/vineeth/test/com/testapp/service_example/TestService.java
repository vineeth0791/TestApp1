package vineeth.test.com.testapp.service_example;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {


    public void onCreate()
    {
        super.onCreate();
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startFlag)
    {
        testRun();
        return START_NOT_STICKY;
    }

    private void testRun()
    {

            Log.d("testService","Inside on startcommand");


    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("testService","test service destroyed");
    }

}
