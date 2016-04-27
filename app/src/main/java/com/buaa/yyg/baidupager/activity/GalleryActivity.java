package com.buaa.yyg.baidupager.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;

import java.util.ArrayList;

/**
 * 画廊效果
 * Created by yyg on 2016/4/27.
 */
public class GalleryActivity extends Activity {

    //画廊
    private Gallery mGallery;
    private ArrayList<Integer> srcData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gallery);

        init();
    }

    private void init() {
        mGallery = (Gallery) findViewById(R.id.myGallery);
        initData();
        mGallery.setAdapter(new myAdapter(this));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //这里模拟四张，实际开发中需要自己去获取
        srcData.add(R.mipmap.img1);
        srcData.add(R.mipmap.img2);
        srcData.add(R.mipmap.img3);
        srcData.add(R.mipmap.img4);
    }


    private class myAdapter extends BaseAdapter {

        private Context mContext;

        public myAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return srcData.size();
        }

        @Override
        public Object getItem(int position) {
            return srcData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(srcData.get(position));
            imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.WRAP_CONTENT));

            return imageView;
        }
    }
}
