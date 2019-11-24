package vineeth.test.com.testapp.data_base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import vineeth.test.com.testapp.kotlin_db_entity.User;
import vineeth.test.com.testapp.kotlin_db_entity.UserDAO;

@Database(entities = {User.class},version = 5)
public abstract class RoomDBHelper extends RoomDatabase {
    private static final String DB_NAME = "test_app";
    private static volatile RoomDBHelper roomDBHelper;
    private static final String CREATE_USER_TABLE_QUERY= "CREATE TABLE user (" +
            "name TEXT," +
            "age INTEGER NOT NULL DEFAULT 0," +
            "id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT)";

    public static synchronized RoomDBHelper getInstance(Context context)
    {
        if(roomDBHelper==null)
        {
            roomDBHelper = create(context);
        }

        return roomDBHelper;
    }

    private static RoomDBHelper create(Context context)
    {
        //ArrayList<Migration> migrations =new ArrayList<>() ;

        return Room.databaseBuilder(context,
                RoomDBHelper.class,
                DB_NAME)
                .addMigrations(migration1_2,migration1_3,migration1_3_4)
                .build();
    }

    public abstract UserDAO getUserDAO();

    private static Migration migration1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("DataBase","Inside DataBase migration 1 to 2 ");
        }
    };

    private static Migration migration1_3  = new Migration(1,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("DataBase","Inside DataBase migration 1 to 3 ");

        }
    };

    private static Migration migration1_3_4  = new Migration(3,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("DataBase","Inside DataBase migration 3 to 5 ");
            database.execSQL("DROP TABLE IF EXISTS user");
            database.execSQL(CREATE_USER_TABLE_QUERY);
        }
    };
}
