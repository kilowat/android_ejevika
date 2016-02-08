package com.example.razor.ejevika.json;

import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.dummy.Product;
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

                for (int i = 0; i < resopnse.length(); i++) {
                    long id = -1;
                    String name = NA;
                    String picture = NA;

                    JSONObject curentCategory = resopnse.getJSONObject(i);
                    if (Utils.contains(curentCategory, KEY_ID)) {
                        id = curentCategory.getInt(KEY_ID);
                    }
                    if (Utils.contains(curentCategory, KEY_NAME)) {
                        name = curentCategory.getString(KEY_NAME);
                    }
                    if (Utils.contains(curentCategory, KEY_PICTURE)) {
                        picture = curentCategory.getString(KEY_PICTURE);
                    }
                    Category category = new Category();
                    category.setId(id);
                    category.setName(name);
                    category.setPicture(picture);
                    if (id != -1 || name.equals(NA)) {
                        categories.add(category);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return categories;
    }

    public static ArrayList<Product> parseProductJSON(JSONArray responce) {
        ArrayList<Product> products = new ArrayList<>();
        if(responce!=null && responce.length()>0) {
            try {
                for (int i = 0; i < responce.length(); i++) {
                    long id = -1;
                    String name = NA;
                    String picture = NA;
                    long sectionId = -1;
                    double price = 0;

                    JSONObject currentPoduct = responce.getJSONObject(i);
                    if(Utils.contains(currentPoduct, KEY_ID)){
                        id = currentPoduct.getLong(KEY_ID);
                    }
                    if(Utils.contains(currentPoduct, KEY_NAME)){
                        name = currentPoduct.getString(KEY_NAME);
                    }
                    if(Utils.contains(currentPoduct, KEY_PICTURE)){
                        picture = currentPoduct.getString(KEY_PICTURE);
                    }
                    if(Utils.contains(currentPoduct, KEY_PRICE)){
                        price = currentPoduct.getDouble(KEY_PRICE);
                    }
                    if(Utils.contains(currentPoduct, KEY_SECTION_ID)){
                        sectionId = currentPoduct.getLong(KEY_SECTION_ID);
                    }
                   if(id!=-1 && !name.equals(NA) && sectionId!=-1){
                       Product product = new Product(id, name, picture, sectionId, price);
                       products.add(product);
                   }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}
