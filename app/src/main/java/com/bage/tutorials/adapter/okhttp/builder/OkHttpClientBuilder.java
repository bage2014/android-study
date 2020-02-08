package com.bage.tutorials.adapter.okhttp.builder;

import com.bage.tutorials.adapter.okhttp.interceptor.TimeCostInterceptor;

import okhttp3.OkHttpClient;

public class OkHttpClientBuilder {

    public OkHttpClient build() {
        return new OkHttpClient.Builder()
                .addInterceptor(new TimeCostInterceptor())
                .build();
    }

}
