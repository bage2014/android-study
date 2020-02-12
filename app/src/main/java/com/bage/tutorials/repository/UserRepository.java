package com.bage.tutorials.repository;

import android.content.Context;

import com.bage.tutorials.component.SharedPreferencesHelper;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.utils.JsonUtils;

public class UserRepository {

    private SharedPreferencesHelper sharedPreferencesHelper;

    public UserRepository(Context context) {
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public void cacheUserToken(User user) {
        sharedPreferencesHelper.insertOrUpdate(AppConstant.userConfigTokenKey, JsonUtils.toJson(user));
    }

    public User getLoginedUser() {
        String token = sharedPreferencesHelper.get(AppConstant.userConfigTokenKey, "");
        if (token.length() > 0) {
            return JsonUtils.fromJson(token, User.class);
        }
        return null;
    }
}
