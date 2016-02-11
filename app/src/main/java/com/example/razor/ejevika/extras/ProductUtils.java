package com.example.razor.ejevika.extras;

import com.android.volley.RequestQueue;
import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.dummy.Product;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.UrlEndPoints.*;


/**
 * Created by razor on 07.02.2016.
 */
public class ProductUtils {

    public static ArrayList<Product> loadProducts(RequestQueue requestQueue, String url,long categoryId){
        JSONArray requestResult =  Requestor.productRequset(requestQueue, url);
        ArrayList<Product> products = Parser.parseProductJSON(requestResult);
        MyApplication.getWritableDatabase().insertProduct(products, categoryId, true);
        return products;
    }
}
