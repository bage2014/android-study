package com.bage.tutorials.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

public class AndroidUtils {

    private static String deviceId = "";

    public static void initDeviceId(Context context) {
        deviceId = String.valueOf(123);
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static Long getUserId() {
        int i = getDeviceId().hashCode();
        if(i < 0){
            i = 0 - i;
        }
        return Long.valueOf(i);
    }

    public static String getIp() {
        return "58.247.238.43";
    }
}