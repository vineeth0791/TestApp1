package vineeth.test.com.testapp.data_base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import java.util.ArrayList;
import java.util.List;

import vineeth.test.com.testapp.kotlin_db_entity.User;
import vineeth.test.com.testapp.kotlin_db_entity.UserDAO;

@Database(entities = {User.class},version = 1)
public abstract class RoomDBHelper extends RoomDatabase {
    private static final String DB_NAME = "test_app.db";
    private static volatile RoomDBHelper roomDBHelper;
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
        #ArrayList<Migration> migrations =new ArrayList<>() ;

        return Room.databaseBuilder(context,
                RoomDBHelper.class,
                DB_NAME)
                .addMigrations(migrations)
                .build();
    }

    public abstract UserDAO getUserDAO();
}
