package vineeth.test.com.testapp.data_base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "test_app";
    private final static int DB_VERSION = 1;

    public final static String EMPLOYEE_TABLE = "employee";
    public final static String EMPLOYEE_NAME = "employee_name";
    public final static String EMPLOYEE_JOINING_DATE = "joining_date";
    private final static String CREATE_EMPLOYEE_SQL_QUERY = "CREATE TABLE "+EMPLOYEE_TABLE+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EMPLOYEE_NAME+" TEXT, " +
           EMPLOYEE_JOINING_DATE+" DATETIME );";


    static Context context1;
    private static DataBaseHelper mInstance;

    public DataBaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
        context1 = context;
    }

    public void onCreate(SQLiteDatabase db)
    {
      Log.d("DB","Inside DataBase Helper On Create");
      db.execSQL(CREATE_EMPLOYEE_SQL_QUERY);

    }

    public void onUpgrade(SQLiteDatabase db,int olderVersion,int newVersion)
    {

    }

    public static synchronized  DataBaseHelper initDB(Context context)
    {
         if(mInstance==null)
         {
             mInstance = new DataBaseHelper(context);
         }

         return mInstance;
    }
}
