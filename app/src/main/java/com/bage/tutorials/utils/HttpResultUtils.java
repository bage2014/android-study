package com.bage.tutorials.utils;

import android.content.Context;

import com.bage.tutorials.http.HttpResult;

import java.util.Objects;

public class HttpResultUtils {

    public static boolean isOk(HttpResult httpResult) {
        LoggerUtils.info(HttpResultUtils.class,"isOk:" + JsonUtils.toJson(httpResult));
        return Objects.nonNull(httpResult) && httpResult.isOk();
    }

    public static void errorCallback(Context context, HttpResult httpResult) {
        LoggerUtils.info(HttpResultUtils.class,"errorCallback:" + JsonUtils.toJson(httpResult));
        ToastUtils.show(context, "请求异常，请检查网络或稍后重试");
    }

}
