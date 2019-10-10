package vineeth.test.com.testapp.service_example;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {

   public MyBinder binder = new MyBinder();
    public void onCreate()
    {
        super.onCreate();
        Log.d("testService","Inside on create");
    }

    public Binder onBind(Intent intent)
    {
        Log.d("testService","Inside on Bind");
        return binder;
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


    @Override
    public boolean onUnbind(Intent intent) {

        Log.d("testService","Inside on un bind");
        return false;
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("testService","Inside test service destroyed");
    }

    public class MyBinder extends Binder
    {
       public TestService getService()
       {
          return TestService.this;
       }
    }

    public void serviceMethod()
    {
        Toast.makeText(this,"Inside service method",Toast.LENGTH_SHORT).show();
        Log.d("testService","Inside Service method");
    }

}
