package com.bage.tutorials.utils;

import android.content.Context;

import com.bage.tutorials.component.SharedPreferencesHelper;
import com.bage.tutorials.config.ServerConfig;
import com.bage.tutorials.constant.AppConstant;

import java.util.Objects;

public class AppConfigUtils {

    private static ServerConfig defaultServerConfig = new ServerConfig();
    private static SharedPreferencesHelper sharedPreferencesHelper = null;

    public static ServerConfig getServerConfig() {
        return defaultServerConfig;
    }

    public static ServerConfig updateServerConfig(ServerConfig serverConfig) {
        if(Objects.nonNull(serverConfig.getServerHost()) && serverConfig.getServerHost().length() > 0){
            defaultServerConfig.setServerHost(serverConfig.getServerHost());
        }
        if(Objects.nonNull(serverConfig.getServerPort()) && serverConfig.getServerPort().length() > 0) {
            defaultServerConfig.setServerPort(serverConfig.getServerPort());
        }
        if(Objects.nonNull(serverConfig.getServerPrefix()) && serverConfig.getServerPrefix().length() > 0) {
            defaultServerConfig.setServerPrefix(serverConfig.getServerPrefix());
        }
        if(Objects.nonNull(serverConfig.getServerProtocol()) && serverConfig.getServerProtocol().length() > 0) {
            defaultServerConfig.setServerProtocol(serverConfig.getServerProtocol());
        }
        return serverConfig;
    }

    public static void reloadServerConfig(Context context) {
        if(Objects.isNull(sharedPreferencesHelper)){
            sharedPreferencesHelper = new SharedPreferencesHelper(context);
        }
        // 准备参数
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServerHost(sharedPreferencesHelper.get(AppConstant.serverConfigHostKey, ""));
        serverConfig.setServerProtocol(sharedPreferencesHelper.get(AppConstant.serverConfigProtocolKey, ""));
        serverConfig.setServerPort(sharedPreferencesHelper.get(AppConstant.serverConfigPortKey, ""));
        // 更新
        updateServerConfig(serverConfig);

        AndroidUtils.initDeviceId(context);
    }
}
