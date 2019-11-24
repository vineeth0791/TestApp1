package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampaignsList {
    @SerializedName("campaigns")
    private List<Campaigns> campaignsList;
    private int statusCode;
    private String status;

    public CampaignsList(int statusCode,List<Campaigns> campaignsList,String status)
    {
        this.campaignsList = campaignsList;
        this.statusCode = statusCode;
        this.status=status;
    }

    public List<Campaigns> getCampaignsList()
    {
        return campaignsList;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getStatus()
    {
        return status;
    }
}
