package vineeth.test.com.testapp.material_design_ex.tab_ex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.R;

public class BottomNavigationViewEx extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view_ex);
        BottomNavigationViewEx.this.enterPictureInPictureMode();
    }
}
