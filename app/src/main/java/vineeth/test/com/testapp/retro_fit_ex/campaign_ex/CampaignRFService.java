package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CampaignRFService {

    @FormUrlEncoded
    @POST("campaigns/list_my_campaigns/")
    public Call<CampaignsList> listMyCampaigns(@Field("secretKey") String secretKey);
}
