package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.RecyclerGallery.MyRecyclerGalleryView;
import com.buaa.yyg.baidupager.RecyclerGallery.RecyclerGalleryAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by yyg on 2016/5/16.
 */
public class RecyclerGalleryActivity extends BaseActivity{
    private static final String TAG = "RecyclerGalleryActivity";
    private ArrayList<String> images = new ArrayList<>();
    private int index;
    private MyRecyclerGalleryView recyclerView;
    private RecyclerGalleryAdapter adapter;
    private ImageView img;

    @Override
    public void initView() {
        setContentView(R.layout.activity_recycler_gallery);
        //得到控件
        recyclerView = (MyRecyclerGalleryView) findViewById(R.id.id_recyclerview_horizontal);
        img = (ImageView) findViewById(R.id.id_content);
    }

    @Override
    public void initData() {
        //获取传递过来的数据
        getListFromIntent();
        //设置recyclerView的数据
        initRecyclerData();
    }

    @Override
    public void initListener() {
        //item条目点击事件，点击哪个小图，大图就显示哪个
        adapter.setOnItemClickListener(new RecyclerGalleryAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //设置大图片
                setImg(position);
                index = position;
            }
        });

        //item条目滑动事件，滑动到哪里，大图就显示哪个
        recyclerView.setOnItemScrollChangeListener(new MyRecyclerGalleryView.OnItemScrollChangeListener() {
            @Override
            public void onChange(View view, int position) {
                //设置大图片
                setImg(position);
            }
        });

        //大图的点击事件，点击之后跳转到设置壁纸的画廊部分
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerGalleryActivity.this, GalleryActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("position", index);
                startActivity(intent);
            }
        });
    }

    @Override
    public void progress(View v) {
    }

    /**
     * 获取传递过来的数据
     */
    private void getListFromIntent() {
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        index = intent.getIntExtra("position", 0);

        Log.d(TAG, "initData: images + position===" + index + images.toString());
    }

    /**
     * 设置recyclerView的数据
     */
    private void initRecyclerData() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.scrollToPositionWithOffset(index, 0);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        adapter = new RecyclerGalleryAdapter(this, images);
        recyclerView.setAdapter(adapter);
    }

    private void setImg(int position) {
        Glide.with(RecyclerGalleryActivity.this)
                .load(images.get(position))
                .error(R.mipmap.turn_right)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1)
                .into(img);
    }

}
