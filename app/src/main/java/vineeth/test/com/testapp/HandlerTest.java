package vineeth.test.com.testapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class HandlerTest extends AppCompatActivity {

    Timer timer;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        timer = new Timer();
        timer.schedule(new TestRunnable(this,"1"),0,1000);
        timer.schedule(new TestRunnable(this,"2"),0,1000);
        timer.schedule(new TestRunnable(this,"3"),0,1000);
        timer.schedule(new TestRunnable(this,"4"),0,1000);

    }

    private class TestRunnable extends TimerTask
    {
        WeakReference<AppCompatActivity> actRef;
        private String activityString;
        public TestRunnable(AppCompatActivity activity, String activityString)
        {
            actRef = new WeakReference<>(activity);
            this.activityString = activityString;
        }

        public void run()
        {

                Log.d("Handler","Inside TimerTask test activityString-"+activityString);

        }
    }

    public void onStop()
    {
        super.onStop();
        timer.cancel();
        timer.purge();
        timer=null;
    }
}
