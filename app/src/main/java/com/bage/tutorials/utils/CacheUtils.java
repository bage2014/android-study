package com.bage.tutorials.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {

    private static Map<String, String> cacheMap = new ConcurrentHashMap<>();


    public static void cache(String key, String value) {
        cacheMap.put(key, value);
    }

    public static void remove(String key) {
        cacheMap.remove(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        return cacheMap.getOrDefault(key, defaultValue);
    }
}
