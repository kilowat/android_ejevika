package com.example.razor.ejevika.extras;

import com.android.volley.RequestQueue;
import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.dummy.Product;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.UrlEndPoints.URL_CATEGORY_ALL;
import static com.example.razor.ejevika.extras.UrlEndPoints.URL_PRODUCT_ALL;

/**
 * Created by razor on 07.02.2016.
 */
public class ProductUtils {
    public static ArrayList<Product> loadProducts(RequestQueue requestQueue){
        JSONArray requestResult =  Requestor.productRequset(requestQueue, URL_PRODUCT_ALL);
        ArrayList<Product> products = Parser.parseProductJSON(requestResult);
        MyApplication.getWritableDatabase().insertProduct(products, true);
        return products;
    }
}
