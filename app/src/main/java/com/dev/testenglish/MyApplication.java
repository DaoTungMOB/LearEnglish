package com.dev.testenglish;

import android.app.Application;

import com.dev.testenglish.database.ObjectBox;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
