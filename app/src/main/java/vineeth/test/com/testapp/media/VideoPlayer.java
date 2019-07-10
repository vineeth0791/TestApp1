package vineeth.test.com.testapp.media;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
import vineeth.test.com.testapp.R;

public class VideoPlayer extends AppCompatActivity {
    VideoView videoView;
    private final static int PICK_VIDEO = 1;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.video_view);
        videoView = findViewById(R.id.videoView);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent,PICK_VIDEO);
    }

    public void onActivityResult(int requestCode,int result,Intent intent)
    {
        if(result==RESULT_OK)
        {
            switch (requestCode)
            {
                case PICK_VIDEO:
                    Uri uri = intent.getData();
                    videoView.setVideoURI(uri);
                    videoView.start();
            }
        }
    }
}
