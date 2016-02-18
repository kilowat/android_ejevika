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
        basketRefresh();
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

    public void showSnakeBar(CoordinatorLayout coordinatorLayout,int countItem, double summ){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, countItem+" товар "+summ+" р", Snackbar.LENGTH_INDEFINITE)
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
        Cursor cursor = MyApplication.getWritableDatabase().getCountBasket();
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        double summ = cursor.getDouble(1);
        this.count = count;
        this.summ = summ;
    }

    public int getCount() {
        return count;
    }

    public double getSumm() {
        return summ;
    }
}
