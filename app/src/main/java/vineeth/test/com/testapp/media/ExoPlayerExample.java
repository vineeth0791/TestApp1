package vineeth.test.com.testapp.media;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import vineeth.test.com.testapp.R;

public class ExoPlayerExample extends AppCompatActivity {

    PlayerView playerView;
    private final static int PICK_VIDEO =1;
    SimpleExoPlayer player;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player_ex);
        playerView = findViewById(R.id.video_player);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent,PICK_VIDEO);
    }

    public void onResume()
    {
        super.onResume();
        hideSystemUi();
    }

    public void onStop()
    {
        super.onStop();
        releasePlayer();
    }

    public void onActivityResult(int requestCode,int result,Intent intent)
    {
        super.onActivityResult(requestCode,result,intent);
        if(result==RESULT_OK)
        {
            switch (requestCode)
            {
                case PICK_VIDEO:
                    Uri uri = intent.getData();
                    initializePlayer(uri);

            }
        }
    }

    private void initializePlayer(Uri uri) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
        player.seekTo(0, 0);
        playerView.hideController();
       // playerView.setControllerHideOnTouch(true);
        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if(visibility==PlayerControlView.VISIBLE)
                {
                    playerView.hideController();
                }
            }
        });

        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri)
    {

        // these are reused for both media sources we create below
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "TestApp"), defaultBandwidthMeter);
        return new ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (player != null) {
            long playbackPosition = player.getCurrentPosition();
            long currentWindow = player.getCurrentWindowIndex();
            boolean playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
