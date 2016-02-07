package com.example.razor.ejevika.fragments;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.acitvities.ProductListActivity;
import com.example.razor.ejevika.adapters.AdapterCategory;
import com.example.razor.ejevika.callbacks.CategoryLoadListener;
import com.example.razor.ejevika.database.DBEjevika;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.extras.CategoryUtils;
import com.example.razor.ejevika.json.Requestor;
import com.example.razor.ejevika.listeners.RecyclerItemClickListener;
import com.example.razor.ejevika.loaders.LoaderCategory;
import com.example.razor.ejevika.network.VolleySingleton;
import com.example.razor.ejevika.tasks.TaskLoadCategory;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment implements CategoryLoadListener,
        LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemClickListener.OnItemClickListener
{

    public AdapterCategory adapterCategory;
    public RecyclerView recyclerView;
    public static final int CATEGORY_ITEM_COUNT = 3;
    public static final String REFRES_TAG = "refresh_tag";
    public static final String ID_CATEGORY = "id_category";
    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerItemClickListener recyclerItemClickListener;


    public CategoryListFragment() {
        // Required empty public constructor
    }

    public CategoryListFragment newInstance(){
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),CATEGORY_ITEM_COUNT));
        adapterCategory = new AdapterCategory(getActivity());
        recyclerItemClickListener = new RecyclerItemClickListener(getActivity(), this);


        recyclerView.setAdapter(adapterCategory);
        recyclerView.addOnItemTouchListener(recyclerItemClickListener);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeCategories);
        swipeRefreshLayout.setOnRefreshListener(this);

       // MyApplication.getWritableDatabase().resetTables();

        getLoaderManager().initLoader(0, savedInstanceState,this).forceLoad();
        return v;
    }

    public void showProductListFragment(long index){
        Intent intent = new Intent(getActivity(),ProductListActivity.class);
        intent.putExtra(ID_CATEGORY, index);
        startActivity(intent);
    }

    /*
    * Call when loading categories from remote server
    * */
    @Override
    public void onCategoriesLoadComplite(ArrayList<Category> categories) {
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        adapterCategory.setCategories(categories);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new LoaderCategory(MyApplication.getAppContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      ArrayList<Category> categories = DBEjevika.getCategoriesFromCursor(data);
        if(categories.isEmpty()){
            new TaskLoadCategory(this).execute();
        }else {
            adapterCategory.setCategories(categories);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }
    @Override
    public void onRefresh() {
        Log.d(REFRES_TAG, "refresh run");
        new TaskLoadCategory(this).execute();
    }

    @Override
    public void onItemClick(View childView, int position) {
        Category category = adapterCategory.getCategory(position);
        long categoryId = category.getId();
        showProductListFragment(categoryId);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
