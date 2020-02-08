package com.bage.tutorials.http;

public interface HttpCallback {

    void onFailure(HttpResult result);

    void onSuccess(HttpResult result);

}
