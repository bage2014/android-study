package com.bage.tutorials.utils;

import com.bage.tutorials.config.ServerConfig;

import java.util.Objects;

public class AppConfigUtils {

    private static ServerConfig serverConfig = new ServerConfig();

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }

    public static ServerConfig updateServerConfig(ServerConfig serverConfig) {
        if(Objects.nonNull(serverConfig.getServerHost()) && serverConfig.getServerHost().length() > 0){
            AppConfigUtils.serverConfig.setServerHost(serverConfig.getServerHost());
        }
        if(Objects.nonNull(serverConfig.getServerPort()) && serverConfig.getServerPort().length() > 0) {
            AppConfigUtils.serverConfig.setServerPort(serverConfig.getServerPort());
        }
        if(Objects.nonNull(serverConfig.getServerPrefix()) && serverConfig.getServerPrefix().length() > 0) {
            AppConfigUtils.serverConfig.setServerPrefix(serverConfig.getServerPrefix());
        }
        if(Objects.nonNull(serverConfig.getServerProtocol()) && serverConfig.getServerProtocol().length() > 0) {
            AppConfigUtils.serverConfig.setServerProtocol(serverConfig.getServerProtocol());
        }
        return serverConfig;
    }

}
