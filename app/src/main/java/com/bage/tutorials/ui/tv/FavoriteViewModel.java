package com.bage.tutorials.ui.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.api.android.AppFavorite;
import com.bage.tutorials.api.android.AppFavoriteQueryParam;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.bage.tutorials.utils.QueryParamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();
    private MutableLiveData<HttpResult> updateResult = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }

    public MutableLiveData<HttpResult> getUpdateResult() {
        return updateResult;
    }

    public void setFavorite(AppFavorite appFavorite) {
        List<HttpParam> params = new ArrayList<>();
        if (Objects.isNull(appFavorite.getId())) {
            params.add(new HttpParam("param", appFavorite));
            doUpdate(AppConstant.urlFavoriteUpdate, params);
        } else {
            params.add(new HttpParam("param", appFavorite.getId()));
            doUpdate(AppConstant.urlFavoriteDelete, params);
        }
    }

    public void doUpdate(String url, List<HttpParam> params) {
        HttpRequests.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(FavoriteViewModel.class, "onFailure httpResult = " + JsonUtils.toJson(httpResult));
                updateResult.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(FavoriteViewModel.class, "onSuccess httpResult = " + JsonUtils.toJson(httpResult));
                updateResult.postValue(httpResult);
            }
        });
    }

    public void queryByUserId(Long userId, int targetPage) {
        List<HttpParam> params = new ArrayList<>();
        AppFavoriteQueryParam param = new AppFavoriteQueryParam();
        QueryParamUtils.setCommonParam(param);
        param.setUserId(userId);
        param.setTargetPage(targetPage);
        params.add(new HttpParam("param", param));
        doQuery(AppConstant.urlFavoriteQuery, params);
    }

    public void doQuery(String url, List<HttpParam> params) {
        HttpRequests.get(url, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(FavoriteViewModel.class, "onFailure httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }

            @Override
            public void onSuccess(HttpResult httpResult) {
                LoggerUtils.info(FavoriteViewModel.class, "onSuccess httpResult = " + JsonUtils.toJson(httpResult));
                result.postValue(httpResult);
            }
        });
    }
}
