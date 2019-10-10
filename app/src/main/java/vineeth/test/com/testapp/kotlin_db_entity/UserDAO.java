package vineeth.test.com.testapp.kotlin_db_entity;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDAO {

    @Insert
    public long insertUser(User user);
}
