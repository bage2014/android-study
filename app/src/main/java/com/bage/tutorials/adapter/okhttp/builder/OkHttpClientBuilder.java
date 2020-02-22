package com.bage.tutorials.adapter.okhttp.builder;

import com.bage.tutorials.adapter.okhttp.interceptor.TimeCostInterceptor;
import com.bage.tutorials.http.ssl.DefaultOkHttpClientBuilder;

import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class OkHttpClientBuilder {

    public OkHttpClient build() {

        OkHttpClient instance = null;
        try {
            instance = new DefaultOkHttpClientBuilder().build()
                    .addInterceptor(new TimeCostInterceptor())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return instance;
    }

}
