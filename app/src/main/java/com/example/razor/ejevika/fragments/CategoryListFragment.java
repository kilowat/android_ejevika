package com.example.razor.ejevika.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.adapters.AdapterCategory;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;
import com.example.razor.ejevika.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.Keys.EndpointCategory.KEY_ID;
import static com.example.razor.ejevika.extras.Keys.EndpointCategory.KEY_NAME;
import static com.example.razor.ejevika.extras.Keys.EndpointCategory.KEY_PICTURE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {

    public AdapterCategory adapterCategory;
    public RecyclerView recyclerView;

    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_list_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapterCategory = new AdapterCategory(getActivity());
        recyclerView.setAdapter(adapterCategory);
        int id = 1;
        String name = "Салаты";
        String picture = "/upload/iblock/5f7/5f729baedbe28701b63ff88f0e020dd4.jpg";

        ArrayList<Category> expected = new ArrayList<>();
        expected.add(new Category(id, picture, name));
        adapterCategory.setCategories(expected);

        return v;
    }

}
