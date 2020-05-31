package com.bage.tutorials.http;

import java.util.Objects;

public class HttpParam {
    String key;
    Object value;

    public HttpParam() {
    }

    public HttpParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
