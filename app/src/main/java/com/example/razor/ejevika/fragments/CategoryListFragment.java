package com.example.razor.ejevika.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.razor.ejevika.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {


    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_list_fragment, container, false);
        return v;
    }

}
