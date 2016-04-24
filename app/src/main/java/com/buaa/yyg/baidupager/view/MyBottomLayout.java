package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;

/**
 * 底部自定义view
 * Created by yyg on 2016/4/22.
 */
public class MyBottomLayout extends LinearLayout implements View.OnClickListener{
    private Context context;
    private RelativeLayout homeLayout;
    private RelativeLayout chosenLayout;
    private RelativeLayout searchLayout;
    private RelativeLayout localLayout;
    private RelativeLayout settingLayout;
    private ICallbackListener iCallbackListener = null;

    public MyBottomLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MyBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom, this);
        findView(view);
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setResidAndColor(0);
    }

    /**
     * 把所有的数据整合一起进行抽取
     */
    private void changeDataItem(int[] resid, int[] color) {
        initDataItem(homeLayout, resid[0], "首页", color[0]);
        initDataItem(chosenLayout, resid[1], "精选", color[1]);
        initDataItem(searchLayout, resid[2], "搜索", color[2]);
        initDataItem(localLayout, resid[3], "本地", color[3]);
        initDataItem(settingLayout, resid[4], "设置", color[4]);
    }

    /**
     * 初始化数据的抽取方法
     * @param resid
     * @param name
     * @param color
     */
    private void initDataItem(View view, int resid, String name, int color) {
        view.findViewById(R.id.tabImg).setBackgroundResource(resid);
        TextView tv = (TextView) view.findViewById(R.id.tabText);
        tv.setText(name);
        tv.setTextColor( (color == 1) ? Color.BLUE : Color.WHITE);
    }

    /**
     * 找到控件的方法
     *
     * @param view
     */
    private void findView(View view) {
        homeLayout = (RelativeLayout) view.findViewById(R.id.homeLayout);
        chosenLayout = (RelativeLayout) view.findViewById(R.id.chosenLayout);
        searchLayout = (RelativeLayout) view.findViewById(R.id.searchLayout);
        localLayout = (RelativeLayout) view.findViewById(R.id.localLayout);
        settingLayout = (RelativeLayout) view.findViewById(R.id.settingLayout);
    }

    /**
     * 控件的监听事件
     */
    private void initListener() {
        homeLayout.setOnClickListener(this);
        chosenLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        localLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }

    /**
     * 控件的点击事件
     * 点击后改变显示的图标和文字的颜色
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeLayout:
                setResidAndColor(0);
                break;
            case R.id.chosenLayout:
                setResidAndColor(1);
                break;
            case R.id.searchLayout:
                setResidAndColor(2);
                break;
            case R.id.localLayout:
                setResidAndColor(3);
                break;
            case R.id.settingLayout:
                setResidAndColor(4);
                break;
        }

        //这里加入了一个接口方法，留给ViewPager去实现
        //功能是点击item后viewPager也会跟着变
        iCallbackListener.click(v.getId());
    }

    /**
     * 设置Res和Color
     * @param i
     */
    public void setResidAndColor(int i) {
        switch (i) {
            case 0:
                changeDataItem(setResid(new int[] {1, 0, 0, 0, 0}),
                        new int[] {1, 0, 0, 0, 0});
                break;
            case 1:
                changeDataItem(setResid(new int[] {0, 1, 0, 0, 0}),
                        new int[] {0, 1, 0, 0, 0});
                break;
            case 2:
                changeDataItem(setResid(new int[] {0, 0, 1, 0, 0}),
                        new int[] {0, 0, 1, 0, 0});
                break;
            case 3:
                changeDataItem(setResid(new int[] {0, 0, 0, 1, 0}),
                        new int[] {0, 0, 0, 1, 0});
                break;
            case 4:
                changeDataItem(setResid(new int[] {0, 0, 0, 0, 1}) ,
                        new int[] {0, 0, 0, 0, 1});
                break;
        }
    }

    /**
     * 统一设置Res的地方，留有参数和返回值
     * @param resid 数组，1表示选中，0表示未选中，导航页5个item都要进行判断
     * @return 返回当前设置的Res，作为changeDataItem的参数
     */
    public int[] setResid(int[] resid) {
       int resHome =  (resid[0] == 1) ?  R.mipmap.image_tabbar_button_home_down : R.mipmap.image_tabbar_button_home;
       int resChosen =  (resid[1] == 1) ?  R.mipmap.image_tabbar_button_chosen_down : R.mipmap.image_tabbar_button_chosen;
       int resSearch =  (resid[2] == 1) ?  R.mipmap.image_tabbar_button_search_down : R.mipmap.image_tabbar_button_search;
       int resLocal =  (resid[3] == 1) ?  R.mipmap.image_tabbar_button_local_down : R.mipmap.image_tabbar_button_local;
       int resSetting =  (resid[4] == 1) ?  R.mipmap.image_tabbar_button_setting_down : R.mipmap.image_tabbar_button_setting;
       return new int[] {resHome, resChosen, resSearch, resLocal, resSetting};
    }

    //初始化接口，由需要实现activity（MainActivity）调用
    //通过findviewbyid获取MyBottomLayout，进行调用
    public void setOnCallbackListener(ICallbackListener iCallbackListener) {
        this.iCallbackListener = iCallbackListener;
    }
    //自定义接口文件，click方法由调用处实现，功能是完成viewpager的滑动
    public interface ICallbackListener {
        public void click(int id);
    }
}