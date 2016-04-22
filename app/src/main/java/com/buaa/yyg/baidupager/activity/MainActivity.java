package com.buaa.yyg.baidupager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.fragment.ChosenFragment;
import com.buaa.yyg.baidupager.fragment.HomeFragment;
import com.buaa.yyg.baidupager.fragment.LocalFragment;
import com.buaa.yyg.baidupager.fragment.SearchFragment;
import com.buaa.yyg.baidupager.fragment.SettingFragment;
import com.buaa.yyg.baidupager.view.MyBottomLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.myViewPager)
    ViewPager myViewPager;
    @Bind(R.id.myBottomLayout)
    MyBottomLayout myBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        myViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    public void initListener() {
        //页面回调
        myBottomLayout.setOnCallbackListener(new MyCallBackListener());
        //ViewPager页面监听 使用add而不是set
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //0是禁止，1是正在滑动，2是停止滑动
                if (state == 2) {
                    //设置滑动ViewPager导航同步变化
                    myBottomLayout.setResidAndColor(myViewPager.getCurrentItem());
                }
            }
        });
    }

    /**
     * 实现回调方法，切换Fragment
     */
    private class MyCallBackListener implements MyBottomLayout.ICallbackListener {

        @Override
        public void click(int id) {
            switch (id) {
                case R.id.homeLayout:
                    myViewPager.setCurrentItem(0);
                    break;
                case R.id.chosenLayout:
                    myViewPager.setCurrentItem(1);
                    break;
                case R.id.searchLayout:
                    myViewPager.setCurrentItem(2);
                    break;
                case R.id.localLayout:
                    myViewPager.setCurrentItem(3);
                    break;
                case R.id.settingLayout:
                    myViewPager.setCurrentItem(4);
                    break;
            }
        }
    }

    /**
     * viewPager的adapter
     */
    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ChosenFragment();
                case 2:
                    return new SearchFragment();
                case 3:
                    return new LocalFragment();
                case 4:
                    return new SettingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            //一共5个页面
            return 5;
        }
    }

    @Override
    public void progress() {
    }

}
