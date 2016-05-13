package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.galleryrecycle.GalleryRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by yyg on 2016/5/13.
 */
public class GalleryRecycleActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private GalleryRecyclerAdapter adapter;
    private ArrayList<String> imageUrl;

    @Override
    public void initView() {
        setContentView(R.layout.activity_gallery_recycle);

        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        imageUrl = intent.getStringArrayListExtra("image");
        int position = intent.getIntExtra("position", 0);
        setRecyclerDate();
        adapter.addItem(imageUrl, position);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void progress(View v) {

    }

    /**
     * 设置recyclerView初始化
     */
    private void setRecyclerDate() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        adapter = new GalleryRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
