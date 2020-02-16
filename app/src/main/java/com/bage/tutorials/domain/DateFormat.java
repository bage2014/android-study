package com.bage.tutorials.domain;

public enum DateFormat {

    YYYY_MM_DD("yyyy-MM-dd"),
    FORMAT_YYYY_MM_DD_HHMM("yyyy-MM-dd HH:mm"),
    FORMAT_YYYY_MM_DD_HHMMSS("yyyy-MM-dd HH:mm:ss");

    private final String format;

    DateFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
