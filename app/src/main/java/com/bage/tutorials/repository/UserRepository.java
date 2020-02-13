package com.bage.tutorials.repository;

import android.content.Context;

import com.bage.tutorials.component.SharedPreferencesHelper;
import com.bage.tutorials.constant.AppConstant;

public class UserRepository {

    private SharedPreferencesHelper sharedPreferencesHelper;

    public UserRepository(Context context) {
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public void cacheUserToken(String jwt) {
        sharedPreferencesHelper.insertOrUpdate(AppConstant.userConfigTokenKey, jwt);
    }

    public String getLoginedUser() {
        return sharedPreferencesHelper.get(AppConstant.userConfigTokenKey, "");
    }


    public void clearUserToken() {
        sharedPreferencesHelper.remove(AppConstant.userConfigTokenKey);
    }
}
