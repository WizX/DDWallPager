package com.buaa.yyg.ddpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by yyg on 2016/4/24.
 */
public class DisScrollView extends ScrollView{

    //手指按下的开始的坐标
    private float startX, startY;
    //手指移动的移动坐标
    private float currentX, currentY;
    //手指抬起的最后坐标
    private float endX, endY;
    //手机按下后的移动距离
    private float distanceX, distanceY;

    public DisScrollView(Context context) {
        super(context);
    }

    public DisScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 事件分发
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //按下事件
            case MotionEvent.ACTION_DOWN:
                //获取坐标
                startX = ev.getX();
                startY = ev.getY();
                break;

            //移动事件
            case MotionEvent.ACTION_MOVE:
                //获取坐标
                currentX = ev.getX();
                currentY = ev.getY();
                distanceX += Math.abs(currentX - startX);
                distanceY += Math.abs(currentY - startY);
                startX = currentX;
                startY = currentY;
                //判断滑动方向
                if (distanceX > distanceY) {    //左右滑动
                    //不拦截事件
                    return false;
                }
                break;
        }
        //上下移动自身处理
        return super.onInterceptTouchEvent(ev);
    }
}
