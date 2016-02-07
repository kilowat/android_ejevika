package com.example.razor.ejevika;

import android.app.Application;
import android.content.Context;

import com.example.razor.ejevika.database.DBEjevika;

/**
 * Created by razor on 01.02.2016.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static DBEjevika database;

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    public synchronized static DBEjevika getWritableDatabase(){
        if(database==null)
            database = new DBEjevika(getAppContext());
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
