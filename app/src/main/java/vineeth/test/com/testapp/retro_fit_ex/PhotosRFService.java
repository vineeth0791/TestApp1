package vineeth.test.com.testapp.retro_fit_ex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotosRFService {

    @GET("/photos")
    Call<List<PhotoModel>> getAllPhotos();
}
