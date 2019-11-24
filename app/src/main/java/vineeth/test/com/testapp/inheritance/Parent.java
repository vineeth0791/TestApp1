package vineeth.test.com.testapp.inheritance;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Parent  extends AppCompatActivity {

    int a=10;
    public static int variable = 1;
    Context context;

    public Parent()
    {

    }
    public Parent(int va,int va2)
    {

    }
    private final void show()
    {
        Toast.makeText(this,"Parent",Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    public void method()
    {
       Log.d("Inheritance","Parent class method");
    }

}
