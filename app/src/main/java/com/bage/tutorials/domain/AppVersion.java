package com.bage.tutorials.domain;


public class AppVersion {

    private long id;
    private int version;
    private String desc;
    private long appFileId;
    private String apkUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getAppFileId() {
        return appFileId;
    }

    public void setAppFileId(long appFileId) {
        this.appFileId = appFileId;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
