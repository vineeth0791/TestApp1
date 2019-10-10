package vineeth.test.com.testapp.room_db_ex;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import vineeth.test.com.testapp.data_base.RoomDBHelper;
import vineeth.test.com.testapp.kotlin_db_entity.User;

public class UserActivityViewModel extends AndroidViewModel {

    public UserActivityViewModel(Application application)
    {
        super(application);
    }

   public void createUser(String name,String age)
   {
       long userId = RoomDBHelper.getInstance(getApplication().getApplicationContext()).getUserDAO().insertUser(
               new User(1,name,Integer.parseInt(age))
       );

       Log.d("UserActivity","After inserting user id "+userId);
       Toast.makeText(getApplication().getApplicationContext(),"After inserting user id "+userId,Toast.LENGTH_SHORT).show();
   }
}
