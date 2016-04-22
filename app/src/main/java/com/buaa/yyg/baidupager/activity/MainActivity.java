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
        //        initPagerContent(new HomeFragment());
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
                    //                    initPagerContent(new HomeFragment());
                    myViewPager.setCurrentItem(0);
                    break;
                case R.id.chosenLayout:
                    //                    initPagerContent(new ChosenFragment());
                    myViewPager.setCurrentItem(1);
                    break;
                case R.id.searchLayout:
                    //                    initPagerContent(new SearchFragment());
                    myViewPager.setCurrentItem(2);
                    break;
                case R.id.localLayout:
                    //                    initPagerContent(new LocalFragment());
                    myViewPager.setCurrentItem(3);
                    break;
                case R.id.settingLayout:
                    //                    initPagerContent(new SettingFragment());
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

    //    /**
    //     * 可通过replace替换之前的Fragment，不过这里用的是回调方法，就不用这个了
    //     */
    //    private void initPagerContent(android.app.Fragment fragment) {
    //        FragmentManager manager = getFragmentManager();
    //        android.app.FragmentTransaction ft = manager.beginTransaction();
    //        ft.replace(R.id.myContent,fragment);
    //        ft.commit();
    //    }

}
