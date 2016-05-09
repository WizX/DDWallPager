package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.buaa.yyg.baidupager.R;

/**
 * Created by yyg on 2016/4/27.
 */
public class IndexActivity extends BaseActivity {
    private RelativeLayout rl_splash;

    @Override
    public void initView() {
        setContentView(R.layout.activity_index);
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);
    }

    @Override
    public void initData() {
        //splash渐变动画
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(1000);
        rl_splash.startAnimation(aa);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //跳转到主界面
                    Thread.sleep(1000);
                    LoadMainui();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void initListener() {
    }

    @Override
    public void progress() {
    }

    /**
     * 跳转到MainActivity
     */
    private void LoadMainui() {
        startActivity(new Intent(IndexActivity.this, MainActivity.class));
        finish();
    }
}
