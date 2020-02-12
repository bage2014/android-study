package com.bage.tutorials.http;

import android.util.Log;

import com.bage.tutorials.http.builder.HttpResultBuilder;
import com.bage.tutorials.utils.JsonUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DefaultHttpCallback implements Callback {

    private static final String TAG = "DefaultHttpCallback";
    private HttpCallback callback;

    public DefaultHttpCallback(HttpCallback callback) {
        this.callback = callback;
    }

    // 处理响应并返回
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        callback.onFailure(new HttpResultBuilder().failure().setCode(500).setMsg("timeout").build());
    }

    // 处理响应并返回
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String result = response.body().string();
        Log.i(TAG, "statusCodeValue = " + response.code());
        Log.i(TAG, "body = " + result);
        try {
            if (result != null) {
                int code = JsonUtils.parseInt(result, "code");
                String data = JsonUtils.parseString(result, "data");
                String msg = JsonUtils.parseString(result, "msg");
                if (200 == code) {
                    callback.onSuccess(new HttpResultBuilder().success().setData(data).build());
                    return;
                } else {
                    callback.onFailure(new HttpResultBuilder().failure().setCode(code).setMsg(msg).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "e = {}" + e.getMessage());
            callback.onFailure(new HttpResultBuilder().failure().setCode(500).setMsg("timeout").build());
        }
    }

}
