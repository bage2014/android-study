package com.bage.tutorials.config;

public class ServerConfig {

    private String serverProtocol = "http";
//    private String serverHost = "10.0.3.2";
    private String serverHost = "101.132.119.250";
    private String serverPort = "8088";
    private String serverPrefix = "tutorials";

    public String getServerProtocol() {
        return serverProtocol;
    }

    public void setServerProtocol(String serverProtocol) {
        this.serverProtocol = serverProtocol;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerPrefix() {
        return serverPrefix;
    }

    public void setServerPrefix(String serverPrefix) {
        this.serverPrefix = serverPrefix;
    }
}
