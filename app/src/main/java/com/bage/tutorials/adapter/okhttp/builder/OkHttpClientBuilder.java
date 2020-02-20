package com.bage.tutorials.adapter.okhttp.builder;

import com.bage.tutorials.adapter.okhttp.interceptor.TimeCostInterceptor;
import com.bage.tutorials.http.ssl.SSLSocketClientFactory;

import okhttp3.OkHttpClient;

public class OkHttpClientBuilder {

    public OkHttpClient build() {

        return new OkHttpClient.Builder()
                .addInterceptor(new TimeCostInterceptor())
                .sslSocketFactory(SSLSocketClientFactory.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClientFactory.getHostnameVerifier())
                .build();
    }

}
