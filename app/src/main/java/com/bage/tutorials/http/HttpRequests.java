package com.bage.tutorials.http;


import android.util.Log;

import com.bage.tutorials.adapter.okhttp.builder.OkHttpClientBuilder;
import com.bage.tutorials.config.ServerConfig;
import com.bage.tutorials.utils.AppConfigUtils;
import com.bage.tutorials.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpRequests {

    private static String TAG = "HttpRequests";

    public static void get(String url, final HttpCallback callback) {
        get(url, null, callback);
    }

    public static void get(String url, List<HttpParam> params, final HttpCallback callback) {
        get(url, params, null, callback);
    }

    public static void get(String url, List<HttpParam> params, List<HttpHeader> headers, final HttpCallback callback) {
        Log.i(TAG, "get url = {}" + url);
        Log.i(TAG, "get headers = {}" + JsonUtils.toJson(headers));
        Log.i(TAG, "get params = {}" + JsonUtils.toJson(params));

        // 构建请求参数
        OkHttpClient client = new OkHttpClientBuilder().build();
        Request request = buildGetRequest(url, params, headers);

        // 发起请求
        client.newCall(request).enqueue(new DefaultHttpCallback(callback));

    }


    public static void post(String url, final HttpCallback callback) {
        post(url, null, callback);
    }

    public static void post(String url, List<HttpParam> params, final HttpCallback callback) {
        post(url, params, null, callback);
    }

    public static void post(String url, List<HttpParam> params, List<HttpHeader> headers, final HttpCallback callback) {
        Log.i(TAG, "post url = {}" + url);
        Log.i(TAG, "post headers = {}" + JsonUtils.toJson(headers));
        Log.i(TAG, "post params = {}" + JsonUtils.toJson(params));

        // 构建请求参数
        OkHttpClient client = new OkHttpClientBuilder().build();
        Request request = buildPostRequest(url, params, headers);

        // 发起请求
        client.newCall(request).enqueue(new DefaultHttpCallback(callback));
    }


    private static Request buildGetRequest(String url, List<HttpParam> params, List<HttpHeader> headers) {
        url = rewriteUrl(url);

        // param
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        if (Objects.nonNull(params)) {
            for (HttpParam param : params) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        // header
        Request.Builder requestBuilder = new Request.Builder();
        if (Objects.nonNull(headers)) {
            for (HttpHeader header : headers) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        // 返回
        return requestBuilder.url(urlBuilder.build()).build();
    }

    private static Request buildPostRequest(String url, List<HttpParam> params, List<HttpHeader> headers) {
        url = rewriteUrl(url);

        // param
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (Objects.nonNull(params)) {
            for (HttpParam param : params) {
                formBodyBuilder.add(param.getKey(), param.getValue());
            }
        }

        // header
        Request.Builder requestBuilder = new Request.Builder();
        if (Objects.nonNull(headers)) {
            for (HttpHeader header : headers) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        // 返回
        return requestBuilder.url(url).post(formBodyBuilder.build()).build();
    }

    private static String rewriteUrl(String url) {
        ServerConfig serverConfig = AppConfigUtils.getServerConfig();
        String serverUrl = "";
        if (Objects.nonNull(serverConfig.getServerPort())) {
            serverUrl = serverConfig.getServerProtocol() + "://" + serverConfig.getServerHost() + ":" + serverConfig.getServerPort() + "/" + serverConfig.getServerPrefix();
        } else {
            serverUrl = serverConfig.getServerProtocol() + "://" + serverConfig.getServerHost() + "/" + serverConfig.getServerPrefix();
        }
        if (serverUrl.endsWith("/") && url.startsWith("/")) {
            url = url.substring(1);
        }
        url = serverUrl + url;

        return url;
    }

}
