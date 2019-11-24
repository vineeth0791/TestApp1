package vineeth.test.com.testapp.room_db_ex;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vineeth.test.com.testapp.data_base.RoomDBHelper;
import vineeth.test.com.testapp.kotlin_db_entity.User;

public class UserActivityViewModel extends AndroidViewModel {

    public LiveData<List<User>> users;

    public UserActivityViewModel(Application application)
    {
        super(application);
    }

   public long createUser(String name,String age)
   {
       User user = new User();
       user.name = name;
       user.age = Integer.parseInt(age);
       long userId = RoomDBHelper.getInstance(getApplication().getApplicationContext()).getUserDAO().insertUser(
               user);

       Log.d("UserActivity","After inserting user id "+userId);

       return userId;
   }

   public void listUsersSync()
   {
       users = RoomDBHelper.getInstance(getApplication().getApplicationContext()).getUserDAO().listAllUsers();
   }
}
