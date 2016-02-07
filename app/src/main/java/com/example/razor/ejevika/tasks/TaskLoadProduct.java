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

/**
 * Created by razor on 07.02.2016.
 */
public class TaskLoadProduct extends AsyncTask<Void,Void,ArrayList<Product>> {

    private ProductLoadListener listener;
    private RequestQueue requestQueue;
    private VolleySingleton volleySingelton;

    public TaskLoadProduct(ProductLoadListener listener){
        this.listener = listener;
        volleySingelton = VolleySingleton.getInstance();
        requestQueue = volleySingelton.getmRequestQueue();
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... params) {
        ArrayList<Product> products = ProductUtils.loadProducts(requestQueue);
        return products;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        if(listener!=null)
            listener.onProductsLoadComplite(products);
    }
}
