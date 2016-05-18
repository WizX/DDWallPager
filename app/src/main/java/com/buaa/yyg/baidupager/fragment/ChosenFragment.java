package com.buaa.yyg.baidupager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.activity.RecyclerGalleryActivity;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.view.DisGridView;
import com.buaa.yyg.baidupager.view.LoadReshView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Random;

/**
 * 精选
 * Created by yyg on 2016/4/22.
 */
public class ChosenFragment extends Fragment {

    private LoadReshView loadview;
    private DisGridView myGridView;
    private ArrayList<String> images = new ArrayList<>();
    private MyAdapter adapter;
    private static final int LOADING_COMPLETE = 1;

    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADING_COMPLETE:
                    adapter.notifyDataSetChanged();

                    //数据加载完成，让LoadReshView的正在加载中textview消失
                    loadview.dataFinish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TextView tv_refresh_top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chosen_fragment, container, false);
        findView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void findView(View view) {
        loadview = (LoadReshView) view.findViewById(R.id.myloadview);
        tv_refresh_top = (TextView) view.findViewById(R.id.tv_refresh_top);
//                myGridView = (DisGridView) view.findViewById(R.id.mGridView);
        myGridView = loadview.getGridView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.get(getActivity()).clearMemory();
    }

    /**
     * 初始化数据
     */
    private void init() {
        loadview.setpullCallBack(new PullClick());
        if (images.size() == 0) {
            initGridData();
        }
        adapter = new MyAdapter(getActivity());
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RecyclerGalleryActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        setOnClickListener();
    }

    private void setOnClickListener() {
        tv_refresh_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadview.getpullScrollView().refeshDate();
            }
        });
    }

    /**
     * GridView数据
     */
    private void initGridData() {
        for (int i = 1; i <= 12; i++) {
            //添加数据
            int num = new Random().nextInt(59) + 1;
            images.add(Constant.URL + "/chosenimg/" + num + ".jpg");
        }
        Log.d("123", images.toString());
    }

    private class PullClick implements LoadReshView.pullCallBack {
        //加载数据
        @Override
        public void loadBottom() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //睡一下
                        Thread.sleep(1000);
                        initGridData();
                        Log.d("123", "底部数据已经加载完了");
                        handle.sendEmptyMessage(LOADING_COMPLETE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void loadTop() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //睡一下
                        Thread.sleep(1000);

                        for (int i = 1; i <= 12; i++) {
                            int num = new Random().nextInt(59) + 1;
                            //添加数据到集合的第一个位置
                            images.add(0, Constant.URL + "/chosenimg/" + num + ".jpg");
                        }

                        Log.d("123", "顶部数据已经加载完了");
                        handle.sendEmptyMessage(LOADING_COMPLETE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * GridView的Adapter
     */
    private class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context mContext) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.chosen_item, parent, false);
                //设置数据
                convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 600));
            }

            Glide.with(ChosenFragment.this)
                    .load(images.get(position))
//                    .placeholder(R.mipmap.chosen1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(1)
//                    .skipMemoryCache(true)    //加入之后会闪屏
                    .crossFade()
                    .into((ImageView) convertView);
            return convertView;
        }
    }
}