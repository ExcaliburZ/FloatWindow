package com.wings.floatwindow.app;

import android.app.Application;

import com.wings.floatwindow.GlobalViewManager;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalViewManager.Companion.addView(
                this,
                R.layout.activity_float
        );
    }
}
