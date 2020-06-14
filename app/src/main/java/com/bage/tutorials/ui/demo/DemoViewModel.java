package com.bage.tutorials.ui.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bage.tutorials.component.viewmodel.HttpResultViewModel;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DemoViewModel extends HttpResultViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }

    public void init(String... param) {
        String userJson = JsonUtils.toJson(param);
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("param", userJson));
        doPost(result, AppConstant.urlDemo, params);
    }

}
