package com.bage.tutorials.utils;

import com.bage.tutorials.component.SharedPreferencesHelper;
import com.bage.tutorials.config.ServerConfig;
import com.bage.tutorials.constant.AppConstant;

import java.util.Objects;

public class AppConfigUtils {

    private static ServerConfig defaultServerConfig = new ServerConfig();

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

    public static void reloadServerConfig(SharedPreferencesHelper sharedPreferencesHelper) {
        // 准备参数
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServerHost(sharedPreferencesHelper.get(AppConstant.serverConfigHostKey, ""));
        serverConfig.setServerProtocol(sharedPreferencesHelper.get(AppConstant.serverConfigProtocolKey, ""));
        // 更新
        updateServerConfig(serverConfig);
    }
}
