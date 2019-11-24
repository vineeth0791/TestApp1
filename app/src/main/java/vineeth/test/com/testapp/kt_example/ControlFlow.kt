package vineeth.test.com.testapp.kt_example

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

public class ControlFlow : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?)
    {
        super.onCreate(savedInstanceState,persistentState);
        executeIf();
    }

    fun executeIf()
    {
        val i=10;
        var iVal=0;
       val result= if(i>=100)
        {
            "I is greater than 100";
            iVal = i;
        }else
       {
           "I is less than 100";
           iVal = i;
       }

        print(result);
    }
}