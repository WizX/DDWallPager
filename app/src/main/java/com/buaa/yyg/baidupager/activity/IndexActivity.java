package com.buaa.yyg.baidupager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.buaa.yyg.baidupager.R;

/**
 * Created by yyg on 2016/4/27.
 */
public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(IndexActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
