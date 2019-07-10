package vineeth.test.com.testapp.inheritance;

import android.app.Activity;
import android.widget.Toast;

public class Parent  extends Activity {

    private static int a=10;

    private final void show()
    {
        Toast.makeText(this,"Parent",Toast.LENGTH_SHORT).show();
    }

}
