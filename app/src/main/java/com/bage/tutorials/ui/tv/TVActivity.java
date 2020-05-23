package com.bage.tutorials.ui.tv;

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

import org.videolan.libvlc.util.VLCVideoLayout;

public class TVActivity extends AppCompatActivity {

    private TVViewModel TVViewModel;
    private VLCMedia vlcMedia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        TVViewModel = ViewModelProviders.of(this, new TVViewModelFactory())
                .get(TVViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TVViewModel.doSomething("some param");
        TVViewModel.getResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                LoggerUtils.info(TVActivity.class, "data = " + httpResult.getData());
                if (httpResult == null) {
                    return;
                }
                // do something
                TVItem item = JsonUtils.fromJson(httpResult.getData(), TVItem.class);
                vlcMedia.init(item);
                vlcMedia.play();
            }
        });
        final VLCVideoLayout view = findViewById(R.id.video_layout);
        vlcMedia = new VLCMedia(view);
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

}
