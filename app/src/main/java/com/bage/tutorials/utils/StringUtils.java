package com.bage.tutorials.utils;

import java.util.Objects;

public class StringUtils {

    public static boolean isNullOrEmpty(String value) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (Objects.equals(value, "")) {
            return true;
        }
        return false;
    }


    public static boolean isNotNullAndNotEmpty(String value) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (Objects.equals(value, "")) {
            return false;
        }
        return true;
    }

}
