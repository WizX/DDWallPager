package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 刷新的View，主要是监听是否滑动到顶部或者底部
 * Created by yyg on 2016/4/25.
 */
public class PullScrollView extends ScrollView{
    private static  final  String LOAD = "load";

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
     * 手指滑动，不停的调用
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
        if (getChildAt(0) != null && getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()) {
            //判断为true，表示拉到底部了，不再调用click
            if (LoadReshView.getBottomOrTop()) {
                return;
            }
            //回调到下一个页面
            icallBack.click(LOAD);
        }
    }

    /**
     * 定义一个底部的接口
     */
    public interface IcallBack {
        public void click(String bottom);
    }

    /**
     * 定义一个方法
     */
    public void setIcallBack(IcallBack icallBack) {
        this.icallBack = icallBack;
    }
}
