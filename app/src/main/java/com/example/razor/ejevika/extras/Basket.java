package com.example.razor.ejevika.extras;

import android.database.Cursor;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.database.DBEjevika;
import com.example.razor.ejevika.dummy.BasketItem;

import java.util.ArrayList;

/**
 * Created by razor on 16.02.2016.
 */
public class Basket {

    private static Basket instance;

    private Basket(){}

    public static Basket getInstance(){
        if(instance == null)
            instance = new Basket();
        return instance;
    }

    public void add(long id){
        MyApplication.getWritableDatabase().insertToBasket(id);
    }
    public ArrayList<BasketItem> getProducts(){
        Cursor cursor = MyApplication.getWritableDatabase().readFromBasket();
        ArrayList<BasketItem> basketItems = DBEjevika.getBasketProductsFromCursor(cursor);
        return basketItems;

    }

}
