package com.bage.tutorials.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpHeader;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<HttpResult> httpResult = new MutableLiveData<>();

    LiveData<HttpResult> getHttpResult() {
        return httpResult;
    }

    public void queryProfile(String jwt) {
        // can be launched in a separate asynchronous job
        List<HttpParam> params = new ArrayList<>();
        List<HttpHeader> headers = new ArrayList<>();
        headers.add(new HttpHeader("Authorization", "Bearer " + jwt));
        HttpRequests.post("/user/profile", params, headers, new HttpCallback() {
            @Override
            public void onFailure(HttpResult result) {
                httpResult.postValue(result);
            }

            @Override
            public void onSuccess(HttpResult result) {
                httpResult.postValue(result);
            }
        });
    }
}
