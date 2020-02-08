package com.bage.tutorials.adapter.okhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TimeCostInterceptor implements Interceptor {

    String TAG = "TimeCostInterceptor";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long start = System.currentTimeMillis();

        Log.i(TAG, "url = " + request.url());

        Response response = chain.proceed(request);

        long end = System.currentTimeMillis();

        Log.i(TAG, "time cost = " + (end - start));

        return response;
    }
}
