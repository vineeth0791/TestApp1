package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GCRetrofitInstance {

    private static Retrofit gcRetrofitInstance;

    private static final String baseUrl = "http://192.168.0.143:8080/";

    public static Retrofit init()
    {
        if(gcRetrofitInstance==null)
        {
            gcRetrofitInstance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }

        return gcRetrofitInstance;
    }
}
