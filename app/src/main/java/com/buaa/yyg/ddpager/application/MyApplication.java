package com.buaa.yyg.ddpager.application;

import android.app.Application;

import org.xutils.x;


/**
 * Created by yyg on 2016/4/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);   //Xutils3初始化

        x.Ext.setDebug(false);   // 设置是否输出debug
    }
}
