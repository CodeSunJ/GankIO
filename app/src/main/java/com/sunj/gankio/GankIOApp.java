package com.sunj.gankio;

import android.app.Application;

import com.sunj.gankio.net.GankIOServiceManager;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/11/13 10:24 PM
 */

public class GankIOApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GankIOServiceManager.getInstance();
    }
}
