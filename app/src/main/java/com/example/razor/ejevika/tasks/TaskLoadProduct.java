package com.example.razor.ejevika.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.callbacks.CategoryLoadListener;
import com.example.razor.ejevika.callbacks.ProductLoadListener;
import com.example.razor.ejevika.dummy.Product;
import com.example.razor.ejevika.extras.ProductUtils;
import com.example.razor.ejevika.network.VolleySingleton;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.UrlEndPoints.URL_PRODUCT_SECTION;

/**
 * Created by razor on 07.02.2016.
 */
public class TaskLoadProduct extends AsyncTask<Void,Void,ArrayList<Product>> {

    private ProductLoadListener listener;
    private RequestQueue requestQueue = VolleySingleton.getInstance().getmRequestQueue();
    private long categoryId = -1;

    public TaskLoadProduct(ProductLoadListener listener){
        this.listener = listener;
     }

    public TaskLoadProduct(ProductLoadListener listener, long categoryId){
        this.listener = listener;
        this.categoryId = categoryId;
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... params) {
        String url =URL_PRODUCT_SECTION+categoryId;
        ArrayList<Product> products = ProductUtils.loadProducts(requestQueue, url,categoryId);
        return products;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        if(listener!=null)
            listener.onProductsLoadComplite(products);
    }
}
