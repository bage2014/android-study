package com.bage.tutorials.ui.feedback;

import androidx.lifecycle.MutableLiveData;

import com.bage.tutorials.component.viewmodel.HttpResultViewModel;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpResult;

public class FeedbackMsgViewModel extends HttpResultViewModel {

    private MutableLiveData<HttpResult> updatableResult = new MutableLiveData<>();

    public MutableLiveData<HttpResult> getUpdatableResult() {
        return updatableResult;
    }

    public void checkForUpdate(int appVersion) {
        doGet(updatableResult, AppConstant.urlCheckForUpdate.replace("{appVersion}", String.valueOf(appVersion)));
    }

}