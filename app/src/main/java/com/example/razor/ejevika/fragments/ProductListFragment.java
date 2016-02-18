package com.example.razor.ejevika.fragments;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.acitvities.MainActivity;
import com.example.razor.ejevika.adapters.AdapterProduct;
import com.example.razor.ejevika.callbacks.ProductLoadListener;
import com.example.razor.ejevika.database.DBEjevika;
import com.example.razor.ejevika.dummy.BasketItem;
import com.example.razor.ejevika.dummy.Product;

import com.example.razor.ejevika.extras.Basket;
import com.example.razor.ejevika.listeners.RecyclerItemClickListener;
import com.example.razor.ejevika.loaders.LoaderProduct;
import com.example.razor.ejevika.tasks.TaskLoadCategory;
import com.example.razor.ejevika.tasks.TaskLoadProduct;


import java.util.ArrayList;

/**
 * Created by razor on 06.02.2016.
 */
public class ProductListFragment extends Fragment implements ProductLoadListener,
        LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, AdapterProduct.OnCartAddClickListener {
    @Nullable

    public static final String SECTION_ID = "section_id";
    private static final String REFRES_TAG = "refresh_tag";
    public int countInRow = 2;
    protected AdapterProduct adapterProduct;
    protected RecyclerView recyclerView;
    protected long sectionId = -1;
    protected ArrayList<Product> products = new ArrayList<>();
    protected SwipeRefreshLayout swipeRefreshLayout;
    public CoordinatorLayout coordinatorLayout;

    public static final String PRODUCTS = "products";
    public Basket basket = Basket.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgent_product_list, container, false);
        Bundle args = getArguments();
        sectionId = args.getLong(SECTION_ID);

        recyclerView = (RecyclerView) v.findViewById(R.id.products_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), countInRow));
        adapterProduct = new AdapterProduct(getActivity());
        recyclerView.setAdapter(adapterProduct);
        adapterProduct.setListener(this);

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeProducts);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (savedInstanceState != null) {
            products = savedInstanceState.getParcelableArrayList(PRODUCTS);
            adapterProduct.setProducts(products);
        } else {
            getLoaderManager().initLoader(LoaderProduct.LOADER_PRODUCT_ID, args, this).forceLoad();
        }


        return v;
    }

    public static ProductListFragment newInstance(long index) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putLong(SECTION_ID, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PRODUCTS, products);
    }

    @Override
    public void onProductsLoadComplite(ArrayList<Product> products) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        this.products = products;
        adapterProduct.setProducts(products);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new LoaderProduct(getActivity(), sectionId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        products = DBEjevika.getProductsFromCursor(data);
        if (products.isEmpty()) {
            new TaskLoadProduct(this, sectionId).execute();
        } else {
            adapterProduct.setProducts(products);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRefresh() {
        Log.d(REFRES_TAG, "refresh run");
        new TaskLoadProduct(this, sectionId).execute();
    }


    @Override
    public void onCartAddClick(View v, Product product) {
        Toast.makeText(getActivity(), product.getName() + "Добавлен в корзину", Toast.LENGTH_SHORT).show();
        basket.add(product,1);
        ArrayList<BasketItem> basketItems = basket.getProducts();
        if(basketItems.size()>0){
            double basketItem = basketItems.get(0).getProductPrice();
            Log.d("test","price "+basketItem);
        }


        basket.showSnakeBar(coordinatorLayout, basket.getCount(), basket.getSumm());


    }
}
