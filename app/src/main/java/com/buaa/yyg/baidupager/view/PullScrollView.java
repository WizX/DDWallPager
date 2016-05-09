package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * 刷新的View，主要是监听是否滑动到顶部或者底部
 * Created by yyg on 2016/4/25.
 */
public class PullScrollView extends ScrollView{
    private static  final  String LOAD = "load";
    boolean flag = true;

    public IcallBack icallBack = null;

    public PullScrollView(Context context) {
        super(context);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 手指滑动，不停的调用，需再加一个标志控制符，保证只加载一次，加载完成后恢复标识符到初始位置
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        check();
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 上下监听
     */
    private void check() {
        //判断是否上拉到底部
        //getChildAt(0).getMeasuredHeight() 内容的高度
        //getScrollY() 滑动的高度
        //getHeight() gridView的高度

        //判断为false，表示已经到底部了，不再调用click方法
        if (!LoadReshView.getBottomOrTop() ) {
            if (getChildAt(0) != null && flag && getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()) {
                //回调到下一个页面
                icallBack.clickBottom(LOAD);
                Log.d("123", "我在底部，回调loadreshview中---flag==" + flag);
                //设置标志控制符
                flag = false;
            } else if (getScrollY() == 0 && flag ) {
                //上拉刷新
                icallBack.clickTop(LOAD);
                Log.d("123", "我在顶部，回调loadreshview中---flag==" + flag);
                //设置标志控制符
                flag = false;
            }
        }
    }

    /**
     * ChosenFragment点击刷新，功能类似于上拉刷新，交给ChosenFragment调用
     */
    public void refeshDate() {
        //刷新数据
        icallBack.clickTop(LOAD);
        Log.d("123", "点击刷新，数据刷新中---");
    }

    //数据加载完毕，将并发控制符置为true
     public void loadingComponent(){
         flag = true;
     }

    /**
     * 定义一个底部的接口
     */
    public interface IcallBack {
        void clickBottom(String bottom);
        void clickTop(String top);
    }

    /**
     * 定义一个方法
     */
    public void setIcallBack(IcallBack icallBack) {
        this.icallBack = icallBack;
    }
}
