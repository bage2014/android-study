package com.bage.tutorials.cache;

import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.utils.CacheUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.StringUtils;

public class UserCache {

    public static void cacheJwt(String jwt) {
        CacheUtils.cache(AppConstant.cacheKeyUserJwt, jwt);
    }


    public static String getJwt() {
        return CacheUtils.getOrDefault(AppConstant.cacheKeyUserJwt, "");
    }


    public static void removeJwt() {
        CacheUtils.remove(AppConstant.cacheKeyUserJwt);
    }

    public static void cacheUser(User user) {
        CacheUtils.cache(AppConstant.cacheKeyUser, JsonUtils.toJson(user));
    }

    public static User getUser() {
        String userJson = CacheUtils.getOrDefault(AppConstant.cacheKeyUser, "");
        if (StringUtils.isNotNullAndNotEmpty(userJson)) {
            return JsonUtils.fromJson(userJson, User.class);
        }
        return null;
    }
}
