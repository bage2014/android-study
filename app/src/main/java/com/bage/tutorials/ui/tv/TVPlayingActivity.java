package com.bage.tutorials.ui.tv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bage.tutorials.R;
import com.bage.tutorials.api.android.TVItem;
import com.bage.tutorials.component.media.MediaPlay;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.Arrays;


public class TVPlayingActivity extends AppCompatActivity {

    private MediaPlay mediaPlay;
    private ImageView fullScreenImageView;
    private ConstraintLayout wrapperView;
    private static boolean isFullscreen = false;
    private AspectRatioFrameLayout contentFrame;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideActionBar();

        setContentView(R.layout.activity_tv_playing);


        Intent intent = getIntent();
        TVItem tvItem = (TVItem) intent.getSerializableExtra("tvItem");
        // todo remove mock data
        tvItem.setUrls(Arrays.asList(
                "http://117.169.120.140:8080/live/cctv-1/.m3u8",
                "http://117.169.120.140:8080/live/cctv-2/.m3u8",
                "http://117.169.120.140:8080/live/cctv-3/.m3u8",
                "http://117.169.120.140:8080/live/cctv-4/.m3u8"));
        LoggerUtils.info(TVPlayingActivity.class, "data = " + JsonUtils.toJson(tvItem));

        mediaPlay = new MediaPlay();
        PlayerView playerView = findViewById(R.id.video_layout);
        mediaPlay.init(TVPlayingActivity.this, playerView, tvItem.getUrl());
        keepScreenOn();

        fullScreenImageView = findViewById(R.id.exo_fullscreen_icon);
        toggleFullscreenImage();
        fullScreenImageView.setOnClickListener((view) -> {
            toggleFullscreen();
        });

        wrapperView = findViewById(R.id.video_wrapper);
        contentFrame = wrapperView.findViewById(R.id.exo_content_frame);

        findViewById(R.id.exo_play).setOnClickListener((view) -> {
            mediaPlay.play();
            keepScreenOn();
        });

        findViewById(R.id.exo_pause).setOnClickListener((view) -> {
            mediaPlay.stop();
            releaseScreenOn();
        });

        findViewById(R.id.exo_back_icon).setOnClickListener((view) -> {
            mediaPlay.stop();
            mediaPlay.release();
            finish();
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
        hideActionBar();
    }

    private void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void releaseScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void hideActionBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public void toggleFullscreen() {
        TVPlayingActivity.isFullscreen = !TVPlayingActivity.isFullscreen;
        LoggerUtils.info(TVPlayingActivity.class, "isFullscreen = " + TVPlayingActivity.isFullscreen);
        if (TVPlayingActivity.isFullscreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            contentFrame.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            contentFrame.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }

        toggleFullscreenImage();

        ViewGroup.LayoutParams layoutParams = wrapperView.getLayoutParams();
        layoutParams.height = TVPlayingActivity.isFullscreen ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        // layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        wrapperView.setLayoutParams(layoutParams);

    }

    private void toggleFullscreenImage() {
        if (TVPlayingActivity.isFullscreen) {
            fullScreenImageView.setImageResource(R.drawable.ic_exo_fullscreen_exit);
        } else {
            fullScreenImageView.setImageResource(R.drawable.ic_exo_fullscreen_enter);
        }
    }

}
