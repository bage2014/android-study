package com.bage.tutorials;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.bage.tutorials.utils.AppConfigUtils;
import com.bage.tutorials.utils.ContextUtils;

public class TutorialsApplication extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        ContextUtils.initPicassoDownloader(mContext);

        AppConfigUtils.reloadServerConfig(mContext);

    }
}
