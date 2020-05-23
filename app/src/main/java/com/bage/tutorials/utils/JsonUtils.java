package com.bage.tutorials.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.Objects;

public class JsonUtils {

    private static Gson gson = new GsonBuilder().create();
    private static JsonParser jsonParser = new JsonParser();

    /**
     * 将一个对象转换成JSON字符串返回
     *
     * @param obj
     * @return jsonString
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }


    /**
     * 将JSON字符串转换成一个对象返回
     *
     * @param json
     * @param classOfT
     * @return javaBean
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }


    /**
     * 解析泛型 <br/>
     * <code>
     * List&lt;User&gt; result = JsonUtils.fromJson(json, new TypeToken&lt;List&lt;User&gt;&gt;() {}.getType()); <br/>
     * Map&lt;String,User&gt; result = JsonUtils.fromJson(json, new TypeToken&lt;HashMap&lt;String,User&gt;&gt;() {}.getType()); <br/>
     * List&lt;RestResponse&gt; result = JsonUtils.fromJson(json, new TypeToken&lt;RestResponse&lt;User&gt;&gt;() {}.getType()); <br/>
     * </code>
     *
     * @param json
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static String parseString(String json, String key) {
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement.isJsonNull()) {
            return null;
        }
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject().toString();
        }
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray().toString();
        }
        return jsonElement.getAsString();
    }

    public static int parseInt(String json, String key) {
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(key);
        if (Objects.nonNull(jsonElement)) {
            return jsonElement.getAsInt();
        }
        throw new RuntimeException("do not contains key = " + key + "");
    }
}

