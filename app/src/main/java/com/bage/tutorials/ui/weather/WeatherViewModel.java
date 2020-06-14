package com.bage.tutorials.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bage.tutorials.component.viewmodel.HttpResultViewModel;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpResult;

public class WeatherViewModel extends HttpResultViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }

    public void query(String ip) {
        String url = AppConstant.urlWeatherWeeklyQuery;
        url = url.replace("{ip}", ip);
        doPost(result, url, null);
    }
}