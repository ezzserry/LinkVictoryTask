package com.example.kryptonworx.taskapplication.helpers;

import android.app.Application;

import com.example.kryptonworx.taskapplication.models.PhotoItem;

import java.util.HashMap;
import java.util.List;


public class MyApplication extends Application {
    public static HashMap<String, List<PhotoItem>> myList;

    @Override
    public void onCreate() {
        super.onCreate();
        myList = new HashMap<>();
    }
}
