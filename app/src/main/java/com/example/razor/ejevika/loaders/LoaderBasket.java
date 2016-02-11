package com.example.razor.ejevika.loaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.example.razor.ejevika.MyApplication;

/**
 * Created by Администратор on 11.02.2016.
 */
public class LoaderBasket extends CursorLoader {
    Context context;
    public LoaderBasket(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = MyApplication.getWritableDatabase().readFromBasket();
        return cursor;
    }
}
