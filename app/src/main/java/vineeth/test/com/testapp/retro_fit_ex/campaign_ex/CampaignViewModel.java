package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignViewModel extends AndroidViewModel {

    MutableLiveData<ListCampaignsResponse> campaigns = new MutableLiveData<>();

    public CampaignViewModel(Application application)
    {
        super(application);

    }

    public MutableLiveData<ListCampaignsResponse> listMyCampaigns()
    {

        CampaignRFService campaignRFService= GCRetrofitInstance.init().create(CampaignRFService.class);
        Call<CampaignsList> call = campaignRFService.listMyCampaigns("9250849197fd4a65ad65c0a8d4b3a69f");
        call.enqueue(new Callback<CampaignsList>() {
            @Override
            public void onResponse(Call<CampaignsList> call, Response<CampaignsList> response) {
                campaigns.setValue(new ListCampaignsResponse(response.body().getStatusCode(),response.body().getStatus(),response.body().getCampaignsList()));

            }

            @Override
            public void onFailure(Call<CampaignsList> call, Throwable t) {
                Log.d("Campaigns","Exception "+t.getMessage());
                campaigns.setValue(new ListCampaignsResponse(-1,t.getMessage(),new ArrayList<Campaigns>()));

            }
        });
        return campaigns;
    }

    public class ListCampaignsResponse
    {
        int statusCode;String status;List<Campaigns> campaigns=new ArrayList<>();
        public ListCampaignsResponse(int statusCode,String status,List<Campaigns> values)
        {
           this.statusCode=statusCode;
           this.status=status;
           this.campaigns = values;
        }

        public String getStatus()
        {
            return status;
        }

        public List<Campaigns> getCampaigns()
        {
            return campaigns;
        }

        public int getStatusCode()
        {
            return statusCode;
        }
    }
}
