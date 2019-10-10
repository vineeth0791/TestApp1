package vineeth.test.com.testapp.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vineeth.test.com.testapp.R;

public class CameraMainActivity extends AppCompatActivity {

    private final static int IMAGE_CAPTURE=1;
    private final static int IMAGE_CAPTURE_URI = 2;
    String captureImgPath;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main_activity);
        captureImageIntent();
    }

    private void captureImageIntent()
    {
        ((Button)findViewById(R.id.capture_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File saveImgFile = getImageFileToSave();
                if(saveImgFile!=null)
                {
                    captureImgPath = saveImgFile.getAbsolutePath();
                    Log.d("Camera","captureImgPath -"+captureImgPath);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                    {
                        Uri saveImageUri = FileProvider.getUriForFile(CameraMainActivity.this,"vineeth.test.com.testapp.provider",saveImgFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                saveImageUri);
                    }else
                    {
                        Uri saveImageUri = Uri.fromFile(saveImgFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,saveImageUri);
                    }

                    startActivityForResult(intent,IMAGE_CAPTURE_URI);
                }else
                {
                    startActivityForResult(intent,IMAGE_CAPTURE);
                }
            }
        });

    }


    private File getImageFileToSave()
    {
        File sdCard = getFilesDir();
        File dir = new File(sdCard + "/AdsKiteMobi/LeadImages");
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
       try {
           File image = File.createTempFile(
                   imageFileName,  /* prefix */
                   ".jpg",         /* suffix */
                   dir      /* directory */
           );

           captureImgPath = image.getAbsolutePath();
           return image;
       }catch(IOException ex)
       {
           ex.printStackTrace();
           Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
           return null;
       }


    }

    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(resultCode==RESULT_OK)
        {
            switch(requestCode)
            {
                case IMAGE_CAPTURE:
                    if(data!=null)
                    {
                        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                        setBitMap(bitmap);
                    }
                    break;
                 case IMAGE_CAPTURE_URI:
                     setImageViewFromPath();
                     break;
            }


        }
    }

    private void setBitMap(Bitmap bitmap)
    {
        ImageView iv = findViewById(R.id.image_view);
        iv.setImageBitmap(bitmap);
    }

    private void setImageViewFromPath()
    {
        ImageView iv=findViewById(R.id.image_view);
        File imgFile = new File(captureImgPath);
        iv.setImageURI(Uri.fromFile(imgFile));
    }
}
