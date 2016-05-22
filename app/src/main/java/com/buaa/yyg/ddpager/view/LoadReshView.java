package com.buaa.yyg.ddpager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.buaa.yyg.ddpager.R;


/**
 * 上拉 刷新，回调使用PullScrollView
 * Created by yyg on 2016/4/25.
 */
public class LoadReshView extends LinearLayout {

    private static final String LOAD = "load";
                //加载底部数据中，显示正在加载textview
    private static final int LOAD_BOTTOM = 0;
                //加载底部数据中，显示正在加载textview
    private static final int LOAD_TOP = 1;
                //加载完成，恢复设置到初始状态，下拉和上拉共用
    private static final int LOADING_COMPLETE = 2;

    //监听底部
    private PullScrollView pullScrollView;
    //数据表格
    private DisGridView mGridView;
    //下拉显示底部正在加载布局
    private LinearLayout llloadbottom;
    //上拉显示顶部正在加载布局
    private RelativeLayout rlloadtop;

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
                case LOAD_BOTTOM:
                    isBottomShow();
                    isShow = true;
                    break;
                case LOAD_TOP:
                    isTopShow();
                    isShow = true;
                    break;
                case LOADING_COMPLETE :
                    isBottomClose();
                    isTopClose();
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
        public void clickBottom(String bottom) {
            //拉到底部了，显示底部正在加载
            if (bottom.equals(LOAD)) {
                pull.loadBottom();
                //正在加载底部数据
                handler.sendEmptyMessage(LOAD_BOTTOM);
                Log.d("123", "LoadReshView 显示底部加载");
            }
        }

        @Override
        public void clickTop(String top) {
            //拉到顶部了，显示顶部正在加载
            if (top.equals(LOAD)) {
                pull.loadTop();
                //正在加载顶部数据
                handler.sendEmptyMessage(LOAD_TOP);
                Log.d("123", "LoadReshView 显示顶部加载");
            }
        }
    }

    /**
     * 加载数据的接口
     */
    public interface pullCallBack {
        //下拉到底部，加载刷新数据
        void loadBottom();

        //上拉到顶部，加载刷新数据
        void loadTop();
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void findView(View view) {
        pullScrollView = (PullScrollView) view.findViewById(R.id.pull_scroll);
        mGridView = (DisGridView) view.findViewById(R.id.mGridView);
        llloadbottom = (LinearLayout) view.findViewById(R.id.llloadbottom);
        rlloadtop = (RelativeLayout) view.findViewById(R.id.rlloadtop);
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
        return isShow;
    }

    /**
     * 显示底部正在加载
     */
    public void isBottomShow() {
        llloadbottom.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏底部正在加载
     */
    public void isBottomClose() {
        llloadbottom.setVisibility(View.GONE);
    }

    /**
     * 显示顶部正在加载
     */
    public void isTopShow() {
        rlloadtop.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏顶部正在加载
     */
    public void isTopClose() {
        rlloadtop.setVisibility(View.GONE);
    }

    /**
     * 数据加载完成，恢复初始标志，下拉和上拉共用
     */
    public void dataFinish() {
        handler.sendEmptyMessage(LOADING_COMPLETE);
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
