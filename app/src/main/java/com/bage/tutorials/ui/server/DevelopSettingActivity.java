package com.bage.tutorials.ui.server;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bage.tutorials.R;
import com.bage.tutorials.config.ServerConfig;
import com.bage.tutorials.utils.AppConfigUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.ToastUtils;

public class DevelopSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.develop_settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new DevelopSettingFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Context context = this;
        findViewById(R.id.settings_fab).setOnClickListener(item -> {
            AppConfigUtils.reloadServerConfig(context);
            ServerConfig serverConfig = AppConfigUtils.getServerConfig();
            ToastUtils.show(context, JsonUtils.toJson(serverConfig));
        });
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
}