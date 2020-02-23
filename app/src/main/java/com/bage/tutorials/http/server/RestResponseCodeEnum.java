package com.bage.tutorials.http.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum RestResponseCodeEnum {
    // 共用
    OK(200),
    SERVER_EXCEPTION(500),
    UNKNOWN_EXCEPTION(0),
    // 用户登录
    USER_LOGIN_ACCOUNT_NOT_EXIST(1001),
    USER_LOGIN_ACCOUNT_LOCKED(1002),
    USER_LOGIN_PASSWORD_ERROR(403);

    private final int code;

    RestResponseCodeEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static Map<Integer,RestResponseCodeEnum> map = new HashMap<>();

    static {
        RestResponseCodeEnum[] values = RestResponseCodeEnum.values();
        if(Objects.nonNull(values)){
            for (RestResponseCodeEnum value : values) {
                map.put(value.code,value);
            }
        }
    }

    public static RestResponseCodeEnum of(int code){
        RestResponseCodeEnum codeEnum = map.get(code);
        return Objects.isNull(codeEnum) ? UNKNOWN_EXCEPTION : codeEnum;
    }

}
