package com.example.razor.ejevika.json;

import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.extras.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.razor.ejevika.extras.Constants.*;
import static com.example.razor.ejevika.extras.Keys.EndpointCategory.*;

import java.util.ArrayList;

/**
 * Created by Администратор on 02.02.2016.
 */
public class Parser {
    /**
     * parse category responce json object
     *
     * @param resopnse
     * @return
     */
    public static ArrayList<Category> parseCategoryJSON(JSONArray resopnse) {
        ArrayList<Category> categories = new ArrayList<>();
        if (resopnse != null && resopnse.length() > 0) {
            try {

                for (int i = 0; i < resopnse.length();i++){
                    long id = -1;
                    String name = NA;
                    String picture = NA;

                    JSONObject curentCategory = resopnse.getJSONObject(i);
                    if (Utils.contains(curentCategory, KEY_NAME)){
                        id = curentCategory.getInt(KEY_NAME);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return categories;
    }
}
