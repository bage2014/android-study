package com.bage.tutorials.ui.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.ui.demo.DemoViewModel;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TVViewModel extends ViewModel {

    private MutableLiveData<HttpResult> result = new MutableLiveData<>();

    LiveData<HttpResult> getResult() {
        return result;
    }

    public void init(String param) {
        String userJson = JsonUtils.toJson(param);
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("param", userJson));
        HttpRequests.post(AppConstant.urlDemo, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult httpResult) {
                LoggerUtils.info(DemoViewModel.class, "onFailure httpResult = " + httpResult);
                List<TVItem> list = new ArrayList<>();
                TVItem item = new TVItem();
                item.setId(1L);
                item.setName("CCTV1");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-1/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(2L);
                item.setName("CCTV2");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-2/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(3L);
                item.setName("CCTV3");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-3/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(4L);
                item.setName("CCTV4");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-4/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(5L);
                item.setName("CCTV5");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-5/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(6L);
                item.setName("CCTV6");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-6/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(7L);
                item.setName("CCTV7");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-7/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(8L);
                item.setName("CCTV8");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-8/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(9L);
                item.setName("CCTV9");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-9/.m3u8");
                list.add(item);

                item = new TVItem();
                item.setId(10L);
                item.setName("CCTV10");
                item.setLogo("XXX");
                item.setUrl("http://117.169.120.140:8080/live/cctv-10/.m3u8");
                list.add(item);

                list.add(item);
                list.add(item);
                list.add(item);
                list.add(item);
                list.add(item);
                list.add(item);
                list.add(item);

                httpResult.setData(JsonUtils.toJson(list));
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
