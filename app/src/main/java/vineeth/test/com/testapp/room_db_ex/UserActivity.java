package vineeth.test.com.testapp.room_db_ex;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import vineeth.test.com.testapp.R;

public class UserActivity extends AppCompatActivity {
    UserActivityViewModel userActivityViewModel;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        userActivityViewModel = ViewModelProviders.of(UserActivity.this).get(UserActivityViewModel.class);
        findViewById(R.id.crate_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEt= findViewById(R.id.user_name_et);
                EditText ageEt = findViewById(R.id.user_age_et);

                userActivityViewModel.createUser(nameEt.getText().toString(),
                        ageEt.getText().toString());
            }
        });

    }


}
