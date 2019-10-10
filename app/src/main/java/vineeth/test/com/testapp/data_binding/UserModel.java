package vineeth.test.com.testapp.data_binding;


import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import vineeth.test.com.testapp.R;

public class UserModel {

    String gender;
    int age;
    ObservableField<String> name = new ObservableField<>();

    private String image;

    public ObservableField<String> getName()
    {
        return name;
    }

    public void setName(ObservableField<String> name)
    {
        this.name=name;

    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

   public void setImage(String image)
   {
       this.image = image;
   }

   public String getImage()
   {
       return image;
   }


   @BindingAdapter({"android:image"})
    public static void loadImage(ImageView view, String imageUrl) {

       RequestOptions options = new RequestOptions()
               .centerCrop()
               .placeholder(R.mipmap.ic_launcher_round)
               .error(R.mipmap.ic_launcher_round);

        Glide.with(view.getContext())
                .applyDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
        Log.d("LoadImage","Image url is "+imageUrl+", image view id "+view.getId());
        // If you consider Picasso, follow the below
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }

    public String getGender()
    {
        return gender;
    }

    public int getAge()
    {
        return age;
    }

}
