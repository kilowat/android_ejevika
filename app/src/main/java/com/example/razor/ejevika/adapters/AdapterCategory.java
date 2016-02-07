package com.example.razor.ejevika.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.network.VolleySingleton;
import static com.example.razor.ejevika.extras.Constants.NA;
import static com.example.razor.ejevika.extras.UrlEndPoints.*;
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
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.category_recycler_item, parent, false);
        ViewHolderCategory vh = new ViewHolderCategory(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolderCategory holder, int position) {
        Category category = mCateogries.get(position);
        holder.categoryName.setText(category.getName());
        String urlThubnail = URL_HOST+category.getPicture();
        imageLoad(urlThubnail, holder);

    }

    @Override
    public int getItemCount() {
        return mCateogries.size();
    }

    private void imageLoad(String urlThubnail, final ViewHolderCategory viewHolderCategory){
        if(!urlThubnail.equals(NA)) {
            mImageLoader.get(urlThubnail, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    viewHolderCategory.categoryPicture.setImageBitmap(response.getBitmap());
                }
            });
        }
    }

     class ViewHolderCategory extends RecyclerView.ViewHolder {

        public TextView categoryName;
        public ImageView categoryPicture;

        public ViewHolderCategory(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryPicture = (ImageView) itemView.findViewById(R.id.category_picture);

        }


    }

}
