package vineeth.test.com.testapp.media;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import vineeth.test.com.testapp.R;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private final static int PICK_AUDIO = 1;
    private MediaPlayer mediaPlayer;

   public void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.media_player);
       mediaPlayer = new MediaPlayer();
       mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


       pickAndPlayAudio();
       stopPlaying();

   }

    private void pickAndPlayAudio()
    {
        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent,PICK_AUDIO);

            }
        });
    }
    private void stopPlaying()
    {
        Button stopButton = findViewById(R.id.stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
       if(true)
       {
           switch (requestCode)
           {
               case PICK_AUDIO:
                   Uri uri = intent.getData();
                   try
                   {

                       mediaPlayer.setDataSource(this,uri);
                       mediaPlayer.prepareAsync();

                       mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                           @Override
                           public void onPrepared(MediaPlayer mediaPlayer) {
                               mediaPlayer.start();
                           }
                       });
                   }catch(IOException e)
                   {
                       Toast.makeText(this,"Inside IO Exception "+e.getMessage(),Toast.LENGTH_LONG).show();
                   }

                   break;
           }
       }
    }

}
