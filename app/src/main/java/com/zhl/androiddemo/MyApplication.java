package com.zhl.androiddemo;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * 描述：
 * Created by zhaohl on 2020-3-3.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static MyApplication getInstance(){
        return myApplication;
    }
}
