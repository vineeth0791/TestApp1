package vineeth.test.com.testapp.media;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import vineeth.test.com.testapp.R;

public class ExoPlayerDashExample extends AppCompatActivity {
    PlayerView playerView ;
    Context context;
    boolean playWhenReady;
    int currentWindow; long playbackPosition;
    private String mediaPath = "https://www.signageserv.ai/media/VID-20190225-WA0007.mp4";
    ExoPlayer player;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player_ex);
        playerView = findViewById(R.id.video_player);
        context= ExoPlayerDashExample.this;
       // initPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    private void initializePlayer()
    {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                new DefaultTrackSelector(),new DefaultLoadControl());
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow,playbackPosition);

        MediaSource mediaSource = buildMediaSource();
        player.prepare(mediaSource,true,false);
    }

    private MediaSource buildMediaSource()
    {
        Uri uri = Uri.parse(mediaPath);
       return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(context,"TestApp")))
                .createMediaSource(uri);
    }
}
