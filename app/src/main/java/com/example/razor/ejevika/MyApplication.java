package com.example.razor.ejevika;

import android.app.Application;
import android.content.Context;

/**
 * Created by razor on 01.02.2016.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
