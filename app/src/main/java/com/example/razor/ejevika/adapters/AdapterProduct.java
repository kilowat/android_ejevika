package com.example.razor.ejevika.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.razor.ejevika.R;
import com.example.razor.ejevika.dummy.Product;
import com.example.razor.ejevika.network.VolleySingleton;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.Constants.NA;
import static com.example.razor.ejevika.extras.Constants.NA;
import static com.example.razor.ejevika.extras.UrlEndPoints.*;
/**
 * Created by Администратор on 08.02.2016.
 */
public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolderProduct> {

    private ArrayList<Product> mProducts = new ArrayList<>();
    private VolleySingleton mVolleySinleton;
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;


    public AdapterProduct(Context context){
        mInflater = LayoutInflater.from(context);
        mVolleySinleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySinleton.getImageLoader();
    }

    public void setProducts(ArrayList<Product> products){
        this.mProducts = products;
        notifyDataSetChanged();
    }

    @Override
    public AdapterProduct.ViewHolderProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_list_recycler_item, parent, false);
        ViewHolderProduct vh = new ViewHolderProduct(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolderProduct holder, int position) {
        holder.productName.setText(mProducts.get(position).getName());
        holder.productPrice.setText(String.valueOf(mProducts.get(position).getPrice()));
        String urlThubnail = URL_HOST+mProducts.get(position).getPicture();
        imageLoad(urlThubnail,holder);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private void imageLoad(String urlThubnail, final ViewHolderProduct viewHolderProduct){
        if(!urlThubnail.equals(NA)) {
            mImageLoader.get(urlThubnail, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    viewHolderProduct.productPicture.setImageBitmap(response.getBitmap());
                }
            });
        }
    }
    public class ViewHolderProduct extends RecyclerView.ViewHolder {
        public TextView productName;
        public ImageView productPicture;
        public TextView productPrice;

        public ViewHolderProduct(View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.product_name);
            productPicture = (ImageView) itemView.findViewById(R.id.product_picture);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
        }
    }
}
