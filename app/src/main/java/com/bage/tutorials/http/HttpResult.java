package com.bage.tutorials.http;

public class HttpResult {
    int statusCode;
    String value;

    public HttpResult() {
    }

    public HttpResult(int statusCode, String value) {
        this.statusCode = statusCode;
        this.value = value;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOk() {
        return statusCode == 200;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", value='" + value + '\'' +
                '}';
    }
}
