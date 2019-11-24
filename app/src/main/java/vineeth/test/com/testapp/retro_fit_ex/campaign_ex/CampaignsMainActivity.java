package vineeth.test.com.testapp.retro_fit_ex.campaign_ex;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vineeth.test.com.testapp.R;
import vineeth.test.com.testapp.databinding.RetrofitCampaignsExBinding;

public class CampaignsMainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final CampaignViewModel viewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);
        RetrofitCampaignsExBinding binding = DataBindingUtil.setContentView(this, R.layout.retrofit_campaigns_ex);
        binding.setCampaignVM(viewModel);
        final ProgressDialog busyDialog = new ProgressDialog(this);
        busyDialog.setMessage("Please Wait");
        busyDialog.setCanceledOnTouchOutside(false);

        binding.getCampaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LiveData<CampaignViewModel.ListCampaignsResponse> response = viewModel.listMyCampaigns();
                busyDialog.show();

                response.observe(CampaignsMainActivity.this, new Observer<CampaignViewModel.ListCampaignsResponse>() {
                    @Override
                    public void onChanged(CampaignViewModel.ListCampaignsResponse listCampaignsResponse) {
                        busyDialog.dismiss();
                        Toast.makeText(CampaignsMainActivity.this,listCampaignsResponse.getStatus()+listCampaignsResponse.getStatusCode(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
