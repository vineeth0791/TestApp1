package vineeth.test.com.testapp.room_db_ex;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vineeth.test.com.testapp.R;
import vineeth.test.com.testapp.kotlin_db_entity.User;

public class UserActivity extends AppCompatActivity {
    UserActivityViewModel userActivityViewModel;
    private Context context;
    private RecyclerView usersListView;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        context = UserActivity.this;
        userActivityViewModel = ViewModelProviders.of(UserActivity.this).get(UserActivityViewModel.class);
        findViewById(R.id.crate_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEt= findViewById(R.id.user_name_et);
                EditText ageEt = findViewById(R.id.user_age_et);

               new SaveUserAsync().execute(new String[]{nameEt.getText().toString(),
                ageEt.getText().toString()});
            }
        });
        usersListView = findViewById(R.id.users_list);

        new DisplayUsers().execute();
    }

    private void displayUsers()
    {
        userActivityViewModel.users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Toast.makeText(context,"Inside observer",Toast.LENGTH_SHORT).show();
                for(User user:users)
                {
                    Log.d("User","User name "+user.name);
                }
                DisplayUserDetailsAdapter adapter = new DisplayUserDetailsAdapter(users);
                usersListView.setLayoutManager(new LinearLayoutManager(context));
                usersListView.setAdapter(adapter);
            }
        });

    }

    private class SaveUserAsync extends AsyncTask<String,Void,Long>
    {
        public Long doInBackground(String... params)
        {
           long id = userActivityViewModel.createUser(params[0],
                    params[1]);
            return id;
        }

        @Override
        public void onPostExecute(Long id)
        {
            Toast.makeText(context,"New User id "+id,Toast.LENGTH_SHORT).show();
        }
    }

    private class DisplayUsers extends AsyncTask<Void,Void,Void>
    {
        public Void doInBackground(Void... params)
        {
           userActivityViewModel.listUsersSync();

           return  null;
        }

        public void onPostExecute(Void users)
        {

            displayUsers();

        }
    }


}
