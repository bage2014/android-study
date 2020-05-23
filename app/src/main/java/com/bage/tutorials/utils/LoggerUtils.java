package com.bage.tutorials.utils;

import android.util.Log;

public class LoggerUtils {

    public static void info(String TAG, String log) {
        Log.i(TAG, log);
    }

    public static void info(Class cls, String log) {
        Log.i(cls.getName(), log);
    }

}
