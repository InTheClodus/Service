package com.nf.service;

import android.app.Application;
import android.content.Context;

/**
 * @Create by Administrator on 2019/11/6.
 * @Author 李建新
 * @用途
 */

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
