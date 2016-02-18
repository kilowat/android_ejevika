package com.example.razor.ejevika.extras;

import android.app.LoaderManager;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.database.DBEjevika;
import com.example.razor.ejevika.dummy.BasketItem;
import com.example.razor.ejevika.dummy.Product;

import java.util.ArrayList;

/**
 * Created by razor on 16.02.2016.
 */
public class Basket {

    private int count = 0;
    private double summ = 0;

    private static Basket instance;

    private Basket(){

    }

    public static Basket getInstance(){
        if(instance == null)
            instance = new Basket();
        return instance;
    }

    public void add(Product product, int count){
        MyApplication.getWritableDatabase().insertToBasket(product,count);
        basketRefresh();
    }

    public ArrayList<BasketItem> getProducts(){
        Cursor cursor = MyApplication.getWritableDatabase().readFromBasket();
        ArrayList<BasketItem> basketItems = DBEjevika.getBasketProductsFromCursor(cursor);
        return basketItems;

    }

    public void showSnakeBar(CoordinatorLayout coordinatorLayout){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, getCount()+" товар "+summ+" р", Snackbar.LENGTH_INDEFINITE)
                .setAction("Оформить", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                                "оформить",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        snackbar.show();
    }

    public void basketRefresh(){
        ArrayList<BasketItem> basketItems = getProducts();

        for(int i = 0;basketItems.size()>i;i++){
            this.summ+=basketItems.get(i).getProductPrice()*basketItems.get(i).getProductCount();
        }

    }

    public int getCount() {
        return count;
    }

    public double getSumm() {
        return summ;
    }
}
