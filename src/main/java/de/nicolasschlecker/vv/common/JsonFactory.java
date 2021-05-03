package de.nicolasschlecker.vv.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.nicolasschlecker.vv.common.serialisation.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class JsonFactory {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    private JsonFactory() {
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object data) {
        return gson.toJson(data);
    }
}
