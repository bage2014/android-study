package com.bage.tutorials.repository;

import android.content.Context;

import com.bage.tutorials.cache.UserCache;
import com.bage.tutorials.component.SharedPreferencesHelper;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.utils.StringUtils;

public class UserRepository {

    private SharedPreferencesHelper sharedPreferencesHelper;

    public UserRepository(Context context) {
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public void cacheUserJwt(String jwt) {
        UserCache.cacheJwt(jwt);
    }

    public void uncacheJwt() {
        UserCache.removeJwt();
    }

    public void persistJwt(String jwt) {
        cacheUserJwt(jwt);
        sharedPreferencesHelper.insertOrUpdate(AppConstant.userConfigTokenKey, jwt);
    }


    public void unPersistJwt() {
        uncacheJwt();
        sharedPreferencesHelper.remove(AppConstant.userConfigTokenKey);
    }

    public String getJwt() {
        String jwt = UserCache.getJwt();
        if (StringUtils.isNotNullAndNotEmpty(jwt)) {
            return jwt;
        }
        return sharedPreferencesHelper.get(AppConstant.userConfigTokenKey, "");
    }

}
