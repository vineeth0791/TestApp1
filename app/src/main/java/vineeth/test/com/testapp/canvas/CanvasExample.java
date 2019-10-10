package vineeth.test.com.testapp.canvas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.R;

public class CanvasExample extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_ex);
        CanvasView cv = (CanvasView)findViewById(R.id.canvas);
        cv.setOnTouchListener(cv);

    }


}
