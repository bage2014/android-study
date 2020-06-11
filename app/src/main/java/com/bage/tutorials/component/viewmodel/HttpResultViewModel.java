package com.bage.tutorials.component.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpHeader;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.List;

public class HttpResultViewModel extends ViewModel {

    protected static void doGet(MutableLiveData<HttpResult> result, String url) {
        doGet(result, url, null);
    }

    protected static void doGet(MutableLiveData<HttpResult> result, String url, List<HttpParam> params) {
        doGet(result, url, params, null);
    }

    protected static void doGet(MutableLiveData<HttpResult> result, String url, List<HttpParam> params, List<HttpHeader> headers) {
        HttpRequests.get(url, params, headers, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(HttpResultViewModel.class, "doGet onFailure httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(HttpResultViewModel.class, "doGet onSuccess httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }
        });
    }

    protected static void doPost(MutableLiveData<HttpResult> result, String url) {
        doPost(result, url, null);
    }

    protected static void doPost(MutableLiveData<HttpResult> result, String url, List<HttpParam> params) {
        doPost(result, url, params, null);
    }

    protected static void doPost(MutableLiveData<HttpResult> result, String url, List<HttpParam> params, List<HttpHeader> headers) {
        HttpRequests.post(url, params, headers, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(HttpResultViewModel.class, "doPost onFailure httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(HttpResultViewModel.class, "doPost onSuccess httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }
        });
    }

}
