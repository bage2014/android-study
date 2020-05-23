package com.bage.tutorials.ui.tv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.R;
import com.bage.tutorials.component.media.VLCMedia;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;

import org.videolan.libvlc.util.VLCVideoLayout;

import java.util.List;

public class TVPlayingActivity extends AppCompatActivity {

    private VLCMedia vlcMedia;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_tv_playing);


        final VLCVideoLayout view = findViewById(R.id.video_layout);
        vlcMedia = new VLCMedia(view);

        Intent intent = getIntent();
        TVItem tvItem = (TVItem) intent.getSerializableExtra("tvItem");
        LoggerUtils.info(TVPlayingActivity.class, "data = " + JsonUtils.toJson(tvItem));
        vlcMedia.init(tvItem);
        vlcMedia.play();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vlcMedia.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vlcMedia.onStop();
    }
}
