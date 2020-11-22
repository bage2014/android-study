package com.bage.tutorials.utils;

import android.content.Context;

public class AndroidUtils {

    private static String deviceId = "";

    public static void initDeviceId(Context context) {
        deviceId = String.valueOf(System.currentTimeMillis());
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static Long getUserId() {
        return Long.valueOf(getDeviceId().hashCode());
    }

    public static String getIp() {
        return "58.247.238.43";
    }
}