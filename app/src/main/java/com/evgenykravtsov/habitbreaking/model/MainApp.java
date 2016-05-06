package com.evgenykravtsov.habitbreaking.model;

import android.app.Application;
import android.content.Context;

public class MainApp extends Application {

    private static MainApp instance;

    ////

    public MainApp() {
        instance = this;
    }

    ////

    public static Context getAppContext() {
        return instance;
    }

    ////


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
