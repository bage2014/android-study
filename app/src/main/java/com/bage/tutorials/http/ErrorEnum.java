package com.bage.tutorials.http;

public enum ErrorEnum {
    Timeout(404, "timeout"),
    Error(500, "error");

    private int code;
    private String msg;


    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
