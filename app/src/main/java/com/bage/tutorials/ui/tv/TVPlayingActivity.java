package com.bage.tutorials.ui.tv;

import android.content.Intent;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_playing);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void back(View v) {
        finish();
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
