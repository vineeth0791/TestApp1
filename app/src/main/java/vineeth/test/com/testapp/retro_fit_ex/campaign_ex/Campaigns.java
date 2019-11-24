package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import com.google.gson.annotations.SerializedName;

public class Campaigns {
    private int id;
    @SerializedName("campaign_name")
    private String campaignName;
    @SerializedName("created_date")
    private String createdDate;

    public Campaigns(int id,String campaignName,String createdDate)
    {
         this.id=id;
         this.campaignName = campaignName;
         this.createdDate = createdDate;
    }

    public String getCampaignName()
    {
        return campaignName;
    }
}
