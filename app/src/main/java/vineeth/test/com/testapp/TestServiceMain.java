package vineeth.test.com.testapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.service_example.TestService;

public class TestServiceMain extends AppCompatActivity {
    private TestService testService;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_service);
        startService();
        stopService();
        bindServiceCall();

        callServiceMethod();

        onUnBindService();
    }

    private void startService()
    {
        Button startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startService(new Intent(TestServiceMain.this,TestService.class));
            }
        });
    }

    private void stopService()
    {
        Button stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(TestServiceMain.this,TestService.class));
            }
        });
    }

    private void bindServiceCall()
    {
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              bindService(new Intent(TestServiceMain.this,TestService.class),serviceConnection,BIND_AUTO_CREATE);

            }
        });
    }

    private void callServiceMethod()
    {
        findViewById(R.id.call_service_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testService==null)
                    Toast.makeText(TestServiceMain.this,"Please bind the service before accessing service",Toast.LENGTH_SHORT).show();
                else
                    testService.serviceMethod();
            }
        });
    }

    ServiceConnection serviceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           TestService.MyBinder binder = (TestService.MyBinder)iBinder;
           testService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            testService=null;
            Toast.makeText(TestServiceMain.this,"Service is disconnected",Toast.LENGTH_SHORT).show();
        }
    };

    private void onUnBindService()
    {
        findViewById(R.id.un_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
            }
        });

    }

}
