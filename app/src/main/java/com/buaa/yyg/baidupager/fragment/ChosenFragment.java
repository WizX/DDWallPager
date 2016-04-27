package com.buaa.yyg.baidupager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.activity.GalleryActivity;
import com.buaa.yyg.baidupager.view.DisGridView;
import com.buaa.yyg.baidupager.view.LoadReshView;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;


/**
 * 精选
 * Created by yyg on 2016/4/22.
 */
public class ChosenFragment extends Fragment {

    private LoadReshView loadview;
    private DisGridView myGridView;
    private ArrayList<Integer> data = new ArrayList<>();
    private myAdapter adapter;

    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    adapter.notifyDataSetChanged();
                    //数据加载完成，让LoadReshView的正在加载中textview消失
                    loadview.dataFinish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
//                myGridView = (DisGridView) view.findViewById(R.id.mGridView);
        myGridView = loadview.getGridView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        loadview.setpullCallBack(new PullClick());
        initGridData();
        adapter = new myAdapter(getActivity());
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), GalleryActivity.class));
            }
        });
    }

    /**
     * GridView数据
     */
    private void initGridData() {
        for (int i = 0; i < 30; i++) {
            //添加数据
            data.add(R.mipmap.nice);
        }
    }

    private class PullClick implements LoadReshView.pullCallBack {
        //加载数据
        @Override
        public void load() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        //睡一下
                        Thread.sleep(2000);
                        initGridData();
                        handle.sendEmptyMessage(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        //刷新数据
        @Override
        public void reFresh() {

        }
    }

    /**
     * GridView的Adapter
     */
    private class myAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater;

        public myAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.chosen_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imgs = (SmartImageView) convertView.findViewById(R.id.smartimg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //设置数据
            convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 250));
            viewHolder.imgs.setBackgroundResource(R.mipmap.nice);
            return convertView;
        }
    }

    static class ViewHolder {
        SmartImageView imgs;
    }
}