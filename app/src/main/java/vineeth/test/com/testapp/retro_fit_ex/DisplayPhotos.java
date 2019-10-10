package vineeth.test.com.testapp.retro_fit_ex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.R;

public class DisplayPhotos extends AppCompatActivity {
   private Context context;
   private EditText et;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.test_view_model);
        context = DisplayPhotos.this;
        et = ((EditText)findViewById(R.id.et));
       /* DisplayPhotosViewModel viewModel = ViewModelProviders.of(this).get(DisplayPhotosViewModel.class);
        TestViewModelBinding testViewModelBinding = DataBindingUtil.setContentView(this, R.layout.test_view_model);
        testViewModelBinding.setViewModel(viewModel);


        final ProgressDialog busyDialog = new ProgressDialog(context);
        busyDialog.setCanceledOnTouchOutside(false);
        busyDialog.setMessage("Please wait...");
        busyDialog.show();

        LiveData<List<PhotoModel>> response =  viewModel.getPhotosFromServer();
        response.observe(this, new Observer<List<PhotoModel>>() {
            @Override
            public void onChanged(List<PhotoModel> photoModels) {
                busyDialog.dismiss();
                for(PhotoModel photoModel:photoModels)
                {
                    Log.d("DisplayPhotos","Photo title"+photoModel.getTitle());
                }
            }
        });


        //get retrofit interface
        PhotosRFService photosRFService = RetrofitClientInstance.getRetrofitInstance().create(PhotosRFService.class);
        Call<List<PhotoModel>> call = photosRFService.getAllPhotos ();
        call.enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                List<PhotoModel> photos = response.body();
                for(PhotoModel photoModel:photos)
                {
                    Log.d("DisplayPhotos","Photo title"+photoModel.getTitle());
                }
                busyDialog.dismiss();
                Toast.makeText(context,"Success"+photos.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                busyDialog.dismiss();
                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
            }
        });*/

       findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               et.setText("Rotate your screen");
            }
        });
    }
}
