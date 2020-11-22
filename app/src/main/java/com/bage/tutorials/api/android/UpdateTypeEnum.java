package com.bage.tutorials.api.android;

public enum UpdateTypeEnum {

    FORCE_UPDATE("FORCE_UPDATE", "强制更新"),
    UNKNOWN("UNKNOWN", "未知-默认");

    private final String code;
    private final String desc;

    UpdateTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UpdateTypeEnum ofCode(String code) {
        UpdateTypeEnum[] values = UpdateTypeEnum.values();
        for (UpdateTypeEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return UpdateTypeEnum.UNKNOWN;
    }

}
