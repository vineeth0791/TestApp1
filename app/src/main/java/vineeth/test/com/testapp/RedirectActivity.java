package vineeth.test.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class RedirectActivity extends Activity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("test2","Inside on create");

    }

    public void onStart()
    {
        super.onStart();
        Log.d("test2","Inside on start");
    }

    public void onResume()
    {
        super.onResume();
        Log.d("test2","Inside onResume");
    }

    public void onPause()
    {
        super.onPause();
        Log.d("test2","Inside onPause");
    }

    public void onStop()
    {
        super.onStop();
        Log.d("test2","Inside onStop");
    }

    public void onRestart()
    {
        super.onRestart();
        Log.d("test2","Inside onRestart");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("test2","Inside onDestroy");
    }

    public void redirect()
    {

    }
}
