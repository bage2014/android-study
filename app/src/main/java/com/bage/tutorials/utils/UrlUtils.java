package com.bage.tutorials.utils;

import com.bage.tutorials.config.ServerConfig;

public class UrlUtils {


    public static String rewriteUrl(String url) {
        ServerConfig serverConfig = AppConfigUtils.getServerConfig();
        String serverUrl = "";
        if (StringUtils.isNotNullAndNotEmpty(serverConfig.getServerPort())) {
            serverUrl = serverConfig.getServerProtocol() + "://" + serverConfig.getServerHost() + ":" + serverConfig.getServerPort() + "/" + serverConfig.getServerPrefix();
        } else {
            serverUrl = serverConfig.getServerProtocol() + "://" + serverConfig.getServerHost() + "/" + serverConfig.getServerPrefix();
        }
        if (serverUrl.endsWith("/") && url.startsWith("/")) {
            url = url.substring(1);
        }
        url = serverUrl + url;

        return url;
    }

}
