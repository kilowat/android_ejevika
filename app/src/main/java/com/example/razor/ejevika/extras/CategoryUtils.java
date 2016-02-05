package com.example.razor.ejevika.extras;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;
import com.example.razor.ejevika.json.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import static com.example.razor.ejevika.extras.UrlEndPoints.*;
/**
 * Created by razor on 04.02.2016.
 */
public class CategoryUtils {
    public static ArrayList<Category> loadCategories(RequestQueue requestQueue){
       JSONArray requestResult =  Requestor.categoryRequest(requestQueue, URL_CATEGORY_ALL);
        ArrayList<Category> categories = Parser.parseCategoryJSON(requestResult);

        return categories;
    }
}
