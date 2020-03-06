package com.bage.tutorials.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {

    private MutableLiveData<HttpResult> httpResult = new MutableLiveData<>();

    public LiveData<HttpResult> getHttpResult() {
        return httpResult;
    }

    public void queryProfile() {
        HttpRequests.post("/user/profile", null, null, new HttpCallback() {
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

    public void updateUser(User user) {
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("user", JsonUtils.toJson(user)));
        HttpRequests.post("/user/update", params, null, new HttpCallback() {
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
    public void updateUserIcon(File file) {
        HttpRequests.upload("/user/icon/upload", file, "file","bage.jpg", null, null, new HttpCallback() {
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
