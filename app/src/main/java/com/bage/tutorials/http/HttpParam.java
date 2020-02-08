package com.bage.tutorials.http;

import java.util.Objects;

public class HttpParam {
    String key;
    String value;

    public HttpParam() {
    }

    public HttpParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpParam httpParam = (HttpParam) o;
        return key.equals(httpParam.key) &&
                value.equals(httpParam.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
