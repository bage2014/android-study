package com.bage.tutorials.ui.tv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bage.tutorials.R;
import com.bage.tutorials.component.media.MediaPlay;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;


public class TVPlayingActivity extends AppCompatActivity {

    private MediaPlay mediaPlay;
    private ImageView fullScreenImageView;
    private ConstraintLayout wrapperView;
    private boolean isFullscreen = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_tv_playing);


        Intent intent = getIntent();
        TVItem tvItem = (TVItem) intent.getSerializableExtra("tvItem");
        LoggerUtils.info(TVPlayingActivity.class, "data = " + JsonUtils.toJson(tvItem));

        mediaPlay = new MediaPlay();
        PlayerView playerView = findViewById(R.id.video_layout);
        mediaPlay.init(TVPlayingActivity.this, playerView, tvItem);

        fullScreenImageView = findViewById(R.id.exo_fullscreen_icon);
        fullScreenImageView.setOnClickListener((view) -> {
            toggleFullscreen();
        });

        wrapperView = findViewById(R.id.video_wrapper);

        findViewById(R.id.exo_play).setOnClickListener((view) -> {
            mediaPlay.play();
        });

        findViewById(R.id.exo_pause).setOnClickListener((view) -> {
            mediaPlay.stop();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlay.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlay.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlay.play();
    }

    private void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private void releaseScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public void toggleFullscreen() {
        isFullscreen = !isFullscreen;
        if (isFullscreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            fullScreenImageView.setImageResource(R.drawable.ic_fullscreen_exit_24dp);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            fullScreenImageView.setImageResource(R.drawable.ic_fullscreen_24dp);
        }
        setVideoViewLayout();
    }

    private void setVideoViewLayout() {
        ViewGroup.LayoutParams layoutParams = wrapperView.getLayoutParams();
        layoutParams.height = isFullscreen ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        // layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        wrapperView.setLayoutParams(layoutParams);

        AspectRatioFrameLayout contentFrame = wrapperView.findViewById(R.id.exo_content_frame);
        if (isFullscreen) {
            contentFrame.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        } else {
            contentFrame.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }
    }

}
