package vineeth.test.com.testapp.retro_fit_ex;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayPhotosViewModel extends AndroidViewModel {

    public DisplayPhotosViewModel(Application application)
    {
        super(application);
    }

    public LiveData<List<PhotoModel>> getPhotosFromServer()
    {
        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
            //Toast.makeText(getApplication().getApplicationContext(),"Toast message", Toast.LENGTH_SHORT).show();
            final MutableLiveData<List<PhotoModel>> mutableLiveData = new MutableLiveData<>();
            Call<List<PhotoModel>> call = RetrofitClientInstance.getRetrofitInstance().create(PhotosRFService.class).getAllPhotos();
            call.enqueue(new Callback<List<PhotoModel>>() {
                @Override
                public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                    mutableLiveData.setValue(response.body());
                    Log.d("DisplayPhotos", "Inside view model Photos" + response.body().size());
                }

                @Override
                public void onFailure(Call<List<PhotoModel>> call, Throwable t) {

                }
            });
            return mutableLiveData;

    }

    public void onCleared()
    {
        super.onCleared();
        Toast.makeText(getApplication().getApplicationContext(),"Inside on cleared",Toast.LENGTH_SHORT).show();
    }
}
