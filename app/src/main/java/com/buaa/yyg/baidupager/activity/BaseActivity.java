package com.buaa.yyg.baidupager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yyg on 2016/4/22.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
    }

    @Override
    public void onClick(View v) {
        progress(v);
    }

    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();
    public abstract void progress(View v);

}
