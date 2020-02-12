package com.bage.tutorials.component;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Objects;

public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean insertOrUpdate(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public boolean batchInsertOrUpdate(Map<String,String> pairs) {
        if(Objects.isNull(pairs)){
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        pairs.forEach((key,value) -> {
            editor.putString(key, value);
        });
        return editor.commit();
    }

    public boolean remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public String get(String key,String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

}