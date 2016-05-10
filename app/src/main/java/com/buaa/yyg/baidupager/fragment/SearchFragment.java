package com.buaa.yyg.baidupager.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.activity.ShowImageActivity;
import com.buaa.yyg.baidupager.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 搜索
 * Created by yyg on 2016/4/22.
 */
public class SearchFragment extends Fragment {

    //显示
    private static final int VISIBLE = 1;
    //隐藏
    private static final int GONE = 2;
    //显示
    private static final int VISIBLE1 = 3;
    //隐藏
    private static final int GONE1 = 4;


    private AbsoluteLayout myLayoutOne, myLayoutTwo;
    //标记
    private int index = 0;
    //进行数据存储
    private ArrayList<ArrayList<String>> data = new ArrayList<>();
    //屏幕的宽
    private int mWidth;
    //手势
    private GestureDetector gest;


    /**
     * 子线程
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VISIBLE:
                    //消失动画
                    setAnim(myLayoutOne, 2000, 0);
                    Log.i("SearchFragment", "第一个消失啦");
                    myLayoutOne.setVisibility(View.GONE);
                    break;
                case GONE:
                    //显示动画
                    setData(myLayoutTwo,index);     //重新随机textview
                    setAnimTwo(myLayoutTwo, 2000, 0);
                    Log.i("SearchFragment", "第二个显示啦");
                    myLayoutTwo.setVisibility(View.VISIBLE);
                    break;
                case VISIBLE1:
                    //消失动画
                    setAnim(myLayoutTwo, 2000, 0);
                    myLayoutTwo.setVisibility(View.GONE);
                    Log.i("SearchFragment", "第二个消失啦");
                    break;
                case GONE1:
                    //显示动画
                    setData(myLayoutOne,index);
                    setAnimTwo(myLayoutOne, 2000, 0);
                    Log.i("SearchFragment", "第一个显示啦");
                    myLayoutOne.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private EditText et_search;
    private ImageView iv_search;


    /**
     * 设置Two动画
     *
     * @param myLayout  控件
     * @param time      显示时间
     * @param deLayTime 延迟时间
     */
    private void setAnimTwo(AbsoluteLayout myLayout, int time, int deLayTime) {
        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        scale.setDuration(time);
        scale.setStartOffset(deLayTime);

        //渐变
        AlphaAnimation alp = new AlphaAnimation(0.0f, 1.0f);
        alp.setDuration(time);
        alp.setStartOffset(deLayTime);

        //组合动画
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(alp);

        //开始动画
        myLayout.startAnimation(set);
    }


    /**
     * 设置One动画
     *
     * @param myLayout  控件
     * @param time      显示时间
     * @param deLayTime 延迟时间
     */
    private void setAnim(AbsoluteLayout myLayout, int time, int deLayTime) {
        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        scale.setDuration(time);
        scale.setStartOffset(deLayTime);

        //渐变
        AlphaAnimation alp = new AlphaAnimation(1.0f, 0.0f);
        alp.setDuration(time);
        alp.setStartOffset(deLayTime);

        //组合动画
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(alp);

        //开始动画
        myLayout.startAnimation(set);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        findView(view);
        return view;
    }

    /**
     * 找控件
     *
     * @param view
     */
    private void findView(View view) {
        //初始化手势
        gest = new GestureDetector(getActivity(), new MyGest());

        //设置触摸，手势
        view.setLongClickable(true);
        view.setOnTouchListener(new MyOnTouch());

        et_search = (EditText) view.findViewById(R.id.et_search);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        myLayoutOne = (AbsoluteLayout) view.findViewById(R.id.myLayoutOne);
        myLayoutTwo = (AbsoluteLayout) view.findViewById(R.id.myLayoutTwo);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //获取屏幕的像素，后续随机分布关键字
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //获取到屏幕的宽
        mWidth = metrics.widthPixels;
        //初始化数据
        initData();
        //设置默认数据
        setData(myLayoutOne, index);
        //设置imageview清空edittext监听
        setListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //两个页面
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    //实际开发中数据还是得从服务器获取
                    ArrayList<String> newDataOne = new ArrayList<>();
                    newDataOne.add("动漫壁纸");
                    newDataOne.add("人物壁纸");
                    newDataOne.add("壁纸");
                    newDataOne.add("比基尼美女");
                    newDataOne.add("制服美女");
                    newDataOne.add("写真艺术");
                    newDataOne.add("性格美女");
                    newDataOne.add("美女车展");
                    newDataOne.add("美女头像");
                    newDataOne.add("QQ头像");
                    newDataOne.add("微信头像");
                    newDataOne.add("av女演员");

                    //添加数据
                    data.add(newDataOne);
                    break;
                case 1:
                    ArrayList<String> newDataTwo = new ArrayList<>();
                    newDataTwo.add("美女性感图片");
                    newDataTwo.add("美女模特");
                    newDataTwo.add("丝袜美女");
                    newDataTwo.add("裙装美女");
                    newDataTwo.add("美女照片");
                    newDataTwo.add("情趣美女");
                    newDataTwo.add("美食图片");
                    newDataTwo.add("纹身图片");
                    newDataTwo.add("动物图片");
                    newDataTwo.add("影视剧照");
                    newDataTwo.add("自拍艺术");
                    //添加数据
                    data.add(newDataTwo);

                    break;
            }
        }
    }

    /**
     * 设置默认的数据
     *
     * @param myLayout
     * @param index
     */
    private void setData(AbsoluteLayout myLayout, int index) {
        //每次加载之前清除
        myLayout.removeAllViews();
        //有数据了之后开始随机分布了
        int startY = 60;
        //X动态生成,判断是第几页的数据，左右两边间距50，用户体验
        for (int i = 0; i < data.get(index).size(); i++) {
            int x = get(40, mWidth - 250);
            int y = startY;

            //文本显示
            final TextView tv = new TextView(getActivity());
            tv.setTextSize(15);
            //随机
            tv.setText(data.get(index).get(i));
            tv.setLines(1);

            //设置随机颜色值
            tv.setTextColor(Color.argb(255, get(30, 210), get(30, 210), get(30, 210)));

            //设置点击变侦听
            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_search.setText(tv.getText());
                    et_search.setSelection(tv.getText().length());
                    //跳转到显示图片activity
                    startActivityNotFinish(tv.getText());
                }
            });

            /**
             * 高，宽，x,y
             */
            AbsoluteLayout.LayoutParams layout = new AbsoluteLayout.LayoutParams(200, 50, x, y);
            myLayout.addView(tv, layout);
            //开始随机分布
            startY += 105;
        }
    }

    /**
     * 跳转到显示图片activity
     */
    private void startActivityNotFinish(CharSequence text) {
        Intent intent = new Intent(getActivity(), ShowImageActivity.class);
        String type = getTypeFromText(text);
        if (!TextUtils.isEmpty(type)) {
            intent.putExtra("type", type);
            startActivity(intent);
        } else {
            UIUtils.showToast(getActivity(), "内容不能为空");
        }
    }

    /**
     * 根据内容text获取type类型，用于调取网络api
     * @param text
     * @return
     */
    private String getTypeFromText(CharSequence text) {
        String type = null;
        if (text == "动漫壁纸") {
            type = "dmbz";
        } else if (text == "人物壁纸") {
            type = "rwbz";
        } else if (text == "壁纸") {
            type = "bz";
        } else if (text == "比基尼美女") {
            type = "bijini";
        } else if (text == "制服美女") {
            type = "zhifu";
        } else if (text == "写真艺术") {
            type = "nvyou";
        } else if (text == "性格美女") {
            type = "xingge";
        } else if (text == "美女车展") {
            type = "rufang";
        } else if (text == "美女头像") {
            type = "meitun";
        } else if (text == "QQ头像") {
            type = "qqtx";
        } else if (text == "微信头像") {
            type = "wxtx";
        } else if (text == "av女演员") {
            type = "av";
        } else if (text == "美女性感图片") {
            type = "xinggan";
        } else if (text == "美女模特") {
            type = "mote";
        } else if (text == "丝袜美女") {
            type = "siwa";
        } else if (text == "裙装美女") {
            type = "qunzhuang";
        } else if (text == "美女照片") {
            type = "meinv";
        } else if (text == "情趣美女") {
            type = "qingqu";
        } else if (text == "美食图片") {
            type = "meishi";
        } else if (text == "纹身图片") {
            type = "wenshen";
        } else if (text == "动物图片") {
            type = "dongwu";
        } else if (text == "影视剧照") {
            type = "yingshi";
        } else if (text == "自拍艺术") {
            type = "tpzp";
        }
        return type;
    }

    /**
     * 随机生成一个坐标
     *
     * @param min
     * @param max
     * @return
     */
    private int get(int min, int max) {
        //从最大到最小的随机数值
        return new Random().nextInt(max)  + min;
    }

    private void setListener() {
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
    }

    /**
     * View的触摸事件
     */
    private class MyOnTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //触摸事件传递给手势
            return gest.onTouchEvent(event);
        }
    }

    /**
     * 手势监听
     */
    private class MyGest implements GestureDetector.OnGestureListener {

        //按下
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        //滑动
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //左边——右边滑动
            if (e1.getX() < e2.getX()) {
                index++;
                //越界处理
                Log.i("MotionEvent", index + "");
                if (index == data.size()) {
                    index = 0;
                    Log.i("MotionEvent", "啊哦");
                }
                //判断当前显示内容
                switch (myLayoutOne.getVisibility()) {
                    //VISIBLE
                    case 0:
                        //动画并且显示，消失
                        setState(VISIBLE);
                        setState(GONE);
                        break;
                    //INVISIBLE
                    case 4:
                        //GONE
                    case 8:
                        //动画并且显示，消失
                        setState(VISIBLE1);
                        setState(GONE1);
                        break;
                }
            }
            return false;
        }
    }

    /**
     * 动态改变状态
     *
     * @param i
     */
    private void setState(final int i) {
        //UI刷新
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(i);
            }
        }).start();
    }
}
