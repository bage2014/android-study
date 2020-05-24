package com.bage.tutorials.ui.tv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bage.tutorials.R;
import com.bage.tutorials.component.media.MediaPlay;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.android.exoplayer2.ui.PlayerView;


public class TVPlayingActivity extends AppCompatActivity {

    private MediaPlay mediaPlay;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_tv_playing);


        Intent intent = getIntent();
        TVItem tvItem = (TVItem) intent.getSerializableExtra("tvItem");
        LoggerUtils.info(TVPlayingActivity.class, "data = " + JsonUtils.toJson(tvItem));

        mediaPlay = new MediaPlay();
        PlayerView view = findViewById(R.id.video_layout);
        mediaPlay.init(TVPlayingActivity.this,view,tvItem);
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
}
