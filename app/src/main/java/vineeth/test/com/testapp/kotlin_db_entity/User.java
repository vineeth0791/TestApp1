package vineeth.test.com.testapp.kotlin_db_entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public final int id;
    public final String name;
    public final int age;

    public User(int id,String name,int age)
    {
         this.id = id;
         this.name = name;
         this.age = age;

    }

}
