package com.bage.tutorials.ui.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

public class DemoViewModel extends ViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }

    public void init(String... param) {
        String userJson = JsonUtils.toJson(param);
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("param", userJson));
        HttpRequests.post(AppConstant.urlDemo, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(DemoViewModel.class, "onFailure httpResult = " + httpResult);
                result.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(DemoViewModel.class, "onSuccess httpResult = " + httpResult);
                result.postValue(httpResult);
            }
        });
    }

}
