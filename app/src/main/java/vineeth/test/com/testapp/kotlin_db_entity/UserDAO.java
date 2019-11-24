package vineeth.test.com.testapp.kotlin_db_entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public long insertUser(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> listAllUsers();
}
