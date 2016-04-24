package com.example.langjian.superlargeimageapp.ui.util;

import android.app.Application;
import android.content.Context;

/**
 * Description
 * Created by langjian on 2016/4/22.
 * Version
 */
public class ApplicationUtil extends Application {

    private static Application instance;

    public static Context getGlobalContext() {
        return instance.getApplicationContext();
    }
    public Context getApplicationContext() {
        return instance.getApplicationContext();
    }
}
