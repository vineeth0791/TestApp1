package vineeth.test.com.testapp.kt_example

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FirstKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("When condition", "Inside on create");
        executeWhen();
    }


    fun main(args: Array<String>): Unit {
        print("First program")

        executeWhen();
    }

    fun executeWhen() {
        Log.d("When condition", "Inside when");
        var numberCondition = 5;
        var condition = when (numberCondition) {
            1 ->
                numberCondition + 1;
            //Log.d("tag","number is $numberCondition")
            2 ->
                numberCondition + 2;
            5 ->
                numberCondition + 5;
            else ->
                numberCondition + 0;

        }

        Log.d("When condition", "Inside when condition $condition");
    }
}