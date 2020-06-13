package com.bage.tutorials.domain;

import java.io.Serializable;

public class UpdateResult implements Serializable {

    private boolean needUpdate;

    private String apkUrl;

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
