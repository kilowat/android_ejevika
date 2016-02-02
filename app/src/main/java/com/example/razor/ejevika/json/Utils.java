package com.example.razor.ejevika.json;

import org.json.JSONObject;

/**
 * Created by razor on 02.02.2016.
 */
public class Utils {
    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
