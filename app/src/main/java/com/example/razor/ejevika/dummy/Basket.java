package com.example.razor.ejevika.dummy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Администратор on 10.02.2016.
 */
public class Basket {

    private ArrayList<Product> products = new ArrayList<>();
    private static Basket instance;

    private Basket() {

    }

    public static Basket getInstance(){
        if(instance==null)
            instance = new Basket();
        return instance;
    }




    public ArrayList<Product> getProducts() {
        return products;
    }

    public void removeProduct(int index){
        try {
            products.remove(index);
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e("test",e.getMessage());
        }

    }
    public void setProduct(Product product) {
        this.products.add(product);
    }
}
