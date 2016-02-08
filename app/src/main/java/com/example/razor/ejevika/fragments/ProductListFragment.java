package com.example.razor.ejevika.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.adapters.AdapterProduct;
import com.example.razor.ejevika.callbacks.ProductLoadListener;
import com.example.razor.ejevika.dummy.Product;
import com.example.razor.ejevika.extras.CategoryUtils;
import com.example.razor.ejevika.extras.ProductUtils;
import com.example.razor.ejevika.extras.UrlEndPoints;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;
import com.example.razor.ejevika.json.Utils;
import com.example.razor.ejevika.network.VolleySingleton;
import com.example.razor.ejevika.tasks.TaskLoadProduct;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by razor on 06.02.2016.
 */
public class ProductListFragment extends Fragment implements ProductLoadListener {
    @Nullable

    public static final String INDEX = "index";
    public int countInRow = 3;
    protected AdapterProduct adapterProduct;
    protected RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgent_product_list, container, false);
        Bundle args = getArguments();
        long index = args.getLong(INDEX);

        recyclerView = (RecyclerView) v.findViewById(R.id.products_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),countInRow));
        adapterProduct = new AdapterProduct(getActivity());
        recyclerView.setAdapter(adapterProduct);
        new TaskLoadProduct(this, index).execute();

        return v;
    }

    public static ProductListFragment newInstance(long index) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putLong(INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onProductsLoadComplite(ArrayList<Product> products) {
        adapterProduct.setProducts(products);
    }
}
