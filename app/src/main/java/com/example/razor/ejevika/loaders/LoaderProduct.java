package com.example.razor.ejevika.loaders;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;

import com.example.razor.ejevika.MyApplication;

/**
 * Created by razor on 08.02.2016.
 */
public class LoaderProduct extends CursorLoader {
    public static int LOADER_PRODUCT_ID = 2;
    private long sectionId = -1;

    public LoaderProduct(Context context){
        super(context);
    }

    public LoaderProduct(Context context, long sectionId) {
        super(context);
        this.sectionId = sectionId;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = MyApplication.getWritableDatabase().readProduct(sectionId);
        return cursor;
    }
}
