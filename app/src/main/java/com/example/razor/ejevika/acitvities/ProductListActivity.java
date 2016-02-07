package com.example.razor.ejevika.acitvities;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.razor.ejevika.R;
import com.example.razor.ejevika.fragments.CategoryListFragment;
import com.example.razor.ejevika.fragments.ProductListFragment;

/**
 * Created by razor on 07.02.2016.
 */
public class ProductListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int idCategory = getIntent().getIntExtra(CategoryListFragment.ID_CATEGORY,0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProductListFragment fragment = ProductListFragment.newInstance(idCategory);
        ft.replace(R.id.content,fragment);
        ft.commit();

    }
}
