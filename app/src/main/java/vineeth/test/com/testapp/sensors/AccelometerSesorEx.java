package vineeth.test.com.testapp.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AccelometerSesorEx extends AppCompatActivity {

    SensorManager sm;
    TextView text1;

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float[] values = sensorEvent.values;
            text1.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.simple_list_item_1);
        text1 = findViewById(android.R.id.text1);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        List list = sm.getSensorList(SensorManager.SENSOR_ACCELEROMETER);
        if(list.size()>=1)
        {
            sm.registerListener(sel,(Sensor)list.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            Toast.makeText(this,"No Sensors found",Toast.LENGTH_LONG).show();
        }

    }

    public void onStop()
    {
        super.onStop();
        sm.unregisterListener(sel);
    }
}
