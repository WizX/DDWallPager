package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.buaa.yyg.baidupager.R;


/**
 * 上拉 刷新，回调使用PullScrollView
 * Created by yyg on 2016/4/25.
 */
public class LoadReshView extends LinearLayout {

    private static final String LOAD = "load";
    private static final int LOADDATA = 1;
    private static final int REFRESH = 2;

    //监听底部
    private PullScrollView pullScrollView;
    //数据表格
    private DisGridView mGridView;
    //下拉显示布局
    private LinearLayout linearLayout;

    public pullCallBack pull = null;
    //是否显示已到底部textview
    private static boolean isShow = false;
    /**
     * 子线程
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADDATA:
                    isBottomShow();
                    isShow = true;
                    break;
                case REFRESH:
                    isBottomClose();
                    isShow = false;
                    pullScrollView.loadingComponent();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public LoadReshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        //加载布局文件
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.pull_load, this);
        findView(view);
        setCallBack();
    }

    /**
     * 实现回调接口
     */
    private void setCallBack() {
        pullScrollView.setIcallBack(new MyIcallback());
    }

    /**
     * 实现接口
     */
    private class MyIcallback implements PullScrollView.IcallBack {

        @Override
        public void click(String bottom) {
            //拉到底部了，显示textview
            if (bottom.equals(LOAD)) {
                pull.load();
                //正在加载数据
                handler.sendEmptyMessage(LOADDATA);
            } else {
                //刷新加载图片
                pull.reFresh();
            }
        }
    }

    /**
     * 加载数据的接口
     */
    public interface pullCallBack {
        //加載
        public void load();

        //刷新
        public void reFresh();
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void findView(View view) {
        pullScrollView = (PullScrollView) view.findViewById(R.id.pull_scroll);
        mGridView = (DisGridView) view.findViewById(R.id.mGridView);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
    }

    public void setpullCallBack(pullCallBack pull) {
        this.pull = pull;
    }

    /**
     * 判斷是否显示操作
     *
     * @return
     */
    public static boolean getBottomOrTop() {
        Log.d("oooo", "isShow " + isShow);
        return isShow;
    }

    /**
     * 显示底部
     */
    public void isBottomShow() {
        linearLayout.setVisibility(View.VISIBLE);
    }

    public void isBottomClose() {
        linearLayout.setVisibility(View.GONE);
    }

    /**
     * 数据加载完成
     */
    public void dataFinish() {
        handler.sendEmptyMessage(REFRESH);
    }

    /**
     * 返回
     *
     * @return
     */
    public PullScrollView getpullScrollView() {
        return pullScrollView;
    }

    /**
     * 返回
     *
     * @return
     */
    public DisGridView getGridView() {
        return mGridView;
    }

}
