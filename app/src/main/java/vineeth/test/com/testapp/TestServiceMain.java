package vineeth.test.com.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import vineeth.test.com.testapp.service_example.TestService;

public class TestServiceMain extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_service);
        startService();
        stopService();
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


}
