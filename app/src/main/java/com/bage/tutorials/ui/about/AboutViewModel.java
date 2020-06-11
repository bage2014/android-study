package com.bage.tutorials.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bage.tutorials.component.viewmodel.HttpResultViewModel;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpResult;

public class AboutViewModel extends HttpResultViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<HttpResult> updatableResult = new MutableLiveData<>();

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Check For Update");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<HttpResult> getUpdatableResult() {
        return updatableResult;
    }

    public void checkForUpdate(int appVersion) {
        doGet(updatableResult, AppConstant.urlCheckForUpdate.replace("{appVersion}", String.valueOf(appVersion)));
    }

}