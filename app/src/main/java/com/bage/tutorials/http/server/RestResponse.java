package com.bage.tutorials.http.server;


import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    /**
     * 请求的状态码
     */
    private int code;
    /**
     * 状态码说明
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 附件信息
     */
    private Object bundle;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getBundle() {
        return bundle;
    }

    public void setBundle(Object bundle) {
        this.bundle = bundle;
    }
}
