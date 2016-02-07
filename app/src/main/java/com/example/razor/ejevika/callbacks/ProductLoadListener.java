package com.example.razor.ejevika.callbacks;


import com.example.razor.ejevika.dummy.Product;

import java.util.ArrayList;

/**
 * Created by razor on 07.02.2016.
 */
public interface ProductLoadListener {
    public void onProductsLoadComplite(ArrayList<Product> products);
}
