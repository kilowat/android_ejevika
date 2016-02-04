package com.example.razor.ejevika.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.callbacks.CategoryLoadListener;
import com.example.razor.ejevika.extras.CategoryUtils;
import com.example.razor.ejevika.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by razor on 04.02.2016.
 */
public class TaskLoadCategory extends AsyncTask<Void ,Void ,ArrayList<Category>> {

    private CategoryLoadListener categoryLoadListenter;
    private RequestQueue requestQueue;
    private VolleySingleton volleySingelton;


    public TaskLoadCategory(CategoryLoadListener categoryLoadListenter){
        this.categoryLoadListenter = categoryLoadListenter;
        volleySingelton = VolleySingleton.getInstance();
        requestQueue = volleySingelton.getmRequestQueue();
    }
    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        ArrayList<Category> categories = CategoryUtils.loadCategories(requestQueue);
        return categories ;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        if(categoryLoadListenter!=null)
            categoryLoadListenter.onCategoriesLoadComplite(categories);
    }
}
