package com.bage.tutorials.domain;


import java.io.Serializable;

public class UpdateResult implements Serializable {

    private boolean needUpdate;

    private AppVersion appVersion;

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }
}
