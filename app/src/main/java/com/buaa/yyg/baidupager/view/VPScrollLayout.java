package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buaa.yyg.baidupager.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 广告轮播
 * Created by yyg on 2016/4/23.
 */
public class VPScrollLayout extends LinearLayout {

    private Context context;
    private static final int what = 0;
    private String TAG = "VPScrollLayout";

    //标记当前页面所在的位置
    private int index = 0;
    private Timer timer;
    private TimerTask timerTask;
    private ViewPager viewpager;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;
    private ImageView imgFour;

    public VPScrollLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public VPScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public VPScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    //更新ui
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what:
                    //获取数据
                    int index = msg.getData().getInt("index");
//                    Log.i(TAG, "index = " + index);
                    setDotView(index);
                    break;
            }
        }
    };

    /**
     * 初始化
     */
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.vp_item, this);
        findView(view);
        //viewPager滑动监听
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                hand(); //把当前position发送给handler
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //对子view实现setOnTouchListener(new ...)监听，在onTouch()方法里，进行拦截。
        // 调用当前子view的onInterceptTouchEvent()方法。
//        viewpager.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        (v.getParent()).requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        (v.getParent()).requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//                return false;
//            }
//        });

    }

    /**
     * 找控件
     *
     * @param views
     */
    private void findView(View views) {
        viewpager = (ViewPager) views.findViewById(R.id.viewpager);
        imgOne = (ImageView) views.findViewById(R.id.imgOne);
        imgTwo = (ImageView) views.findViewById(R.id.imgTwo);
        imgThree = (ImageView) views.findViewById(R.id.imgThree);
        imgFour = (ImageView) views.findViewById(R.id.imgFour);
    }

    /**
     * 发送消息
     */
    private void hand() {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = what;
        handler.sendMessage(msg);
    }

    /**
     * 设置轮播，定时更新数据
     */
    private void setDotView(int index) {
        viewpager.setCurrentItem(index);
//        Log.i(TAG, "indexs = " + index);
        switch (index) {
            case 0:
                setRes(new int[] {1, 0, 0, 0});
                break;
            case 1:
                setRes(new int[] {0, 1, 0, 0});
                break;
            case 2:
                setRes(new int[] {0, 0, 1, 0});
                break;
            case 3:
                setRes(new int[] {0, 0, 0, 1});
                break;
        }
    }

    /**
     * 设置所有小点图片
     */
    private void setRes(int[] res) {
        imgOne.setBackgroundResource( (res[0] == 1) ? R.mipmap.point_selected : R.mipmap.point_normal);
        imgTwo.setBackgroundResource( (res[1] == 1) ? R.mipmap.point_selected : R.mipmap.point_normal);
        imgThree.setBackgroundResource( (res[2] == 1) ? R.mipmap.point_selected : R.mipmap.point_normal);
        imgFour.setBackgroundResource( (res[3] == 1) ? R.mipmap.point_selected : R.mipmap.point_normal);
    }

    public void setPagerFromTime(int delayTime) {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //通知handler改变view
                hand();

                //每运行一次+1
                index++;
                //保证循环轮播
                if (index == 4) {
                    index = 0;
                }
            }
        };
        timer.schedule(timerTask, delayTime, delayTime);
    }

    public ViewPager getViewpager() {
        return viewpager;
    }

}

