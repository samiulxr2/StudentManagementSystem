package com.sms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private JsonUtil() {
    }

    public static Gson getGson() {
        return gson;
    }

}