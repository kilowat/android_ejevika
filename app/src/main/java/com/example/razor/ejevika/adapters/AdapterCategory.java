package com.example.razor.ejevika.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.network.VolleySingleton;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by razor on 01.02.2016.
 */
public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolderCategory> {

    private ArrayList<Category> mCateogries = new ArrayList<>();
    private VolleySingleton mVolleySinleton;
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;

    public AdapterCategory(Context context) {
        mInflater = LayoutInflater.from(context);
        mVolleySinleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySinleton.getImageLoader();
    }

    public void setCategories(ArrayList<Category> categories){
        this.mCateogries = categories;
        notifyDataSetChanged();
    }

    @Override
    public AdapterCategory.ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AdapterCategory.ViewHolderCategory holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    static class ViewHolderCategory extends RecyclerView.ViewHolder{

        public ViewHolderCategory(View itemView) {
            super(itemView);
        }
    }
}
