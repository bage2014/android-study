package com.bage.tutorials.ui.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.ui.demo.DemoViewModel;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

public class TVViewModel extends ViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }


    public void queryAll() {
        List<HttpParam> params = new ArrayList<>();
        doQuery(AppConstant.urlTVQueryAll,params);
    }

    public void query(int targetPage) {
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("targetPage", targetPage));
        doQuery(AppConstant.urlTVQuery,params);
    }

    public void doQuery(String url,List<HttpParam> params) {
        HttpRequests.get(url, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(DemoViewModel.class, "onFailure httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(DemoViewModel.class, "onSuccess httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }
        });
    }
}
