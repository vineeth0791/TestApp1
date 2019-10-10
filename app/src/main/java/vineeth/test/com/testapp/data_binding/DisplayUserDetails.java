package vineeth.test.com.testapp.data_binding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import vineeth.test.com.testapp.R;
import vineeth.test.com.testapp.databinding.UserProfileBinding;

public class DisplayUserDetails extends AppCompatActivity {
     UserProfileBinding userProfileBinding;
     UserModel userModel;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userProfileBinding = DataBindingUtil.setContentView(this, R.layout.user_profile);
        userModel = new UserModel();
        userModel.name.set("Vineeth");

        userModel.setGender("Male");
        userModel.setImage("https://www.signageserv.ai/media/Photo_1544581420654.png");
        userProfileBinding.setUser(userModel);


         userProfileBinding.setHandlers(new HandleClicks());


    }

    public class HandleClicks{

        public void onClicked(View view)
        {

            userModel.name.set(userProfileBinding.editText2.getText().toString());
            Toast.makeText(DisplayUserDetails.this,"Name has been changed",Toast.LENGTH_SHORT).show();
        }
    }
}
