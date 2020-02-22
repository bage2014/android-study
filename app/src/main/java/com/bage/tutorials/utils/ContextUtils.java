package com.bage.tutorials.utils;

import android.content.Context;

import com.bage.tutorials.adapter.okhttp.builder.OkHttpClientBuilder;
import com.bage.tutorials.adapter.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class ContextUtils {

    public static void initPicassoDownloader(Context context) {
        Picasso.setSingletonInstance(new Picasso.Builder(context).
                downloader(new OkHttp3Downloader(new OkHttpClientBuilder().build()))
                .build());
    }

}
