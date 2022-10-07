package com.codekolih.producciontablet.aciones;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class GsonUtils {

    public static <T> List<T> parseList(String json, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public static JSONObject toJSON(Object o) {
        Gson gson = new Gson();

        String s = gson.toJson(o);
        try {
            return new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
