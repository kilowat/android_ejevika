package com.example.razor.ejevika.loaders;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.database.DBEjevika;
import com.example.razor.ejevika.dummy.Category;

import java.util.ArrayList;

/**
 * Created by razor on 06.02.2016.
 */
public class LoaderCategory extends CursorLoader {

    public static final int LOADER_CATEGORY_ID = 1;

    public LoaderCategory(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = MyApplication.getWritableDatabase().readCategory();
        return cursor;
    }

  }
