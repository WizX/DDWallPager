package com.buaa.yyg.ddpager.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.buaa.yyg.ddpager.R;
import com.buaa.yyg.ddpager.RecyclerGallery.MyRecyclerGalleryView;
import com.buaa.yyg.ddpager.RecyclerGallery.RecyclerGalleryAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
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
    private String dirAbsolutePath;

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
                if (TextUtils.isEmpty(dirAbsolutePath)) {
                    //文件夹路径为空，加载网络图片
                    setImgFromUrl(position);
                } else {
                    setImgFromDir(position);
                }
                index = position;
            }
        });

        //item条目滑动事件，滑动到哪里，大图就显示哪个
        recyclerView.setOnItemScrollChangeListener(new MyRecyclerGalleryView.OnItemScrollChangeListener() {
            @Override
            public void onChange(View view, int position) {
                //设置大图片
                if (TextUtils.isEmpty(dirAbsolutePath)) {
                    //文件夹路径为空，加载网络图片
                    setImgFromUrl(position);
                } else {
                    setImgFromDir(position);
                }
                //改变当前位置索引
                index = position;
            }
        });

        //大图的点击事件，点击之后跳转到设置壁纸的画廊部分
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerGalleryActivity.this, GalleryActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("position", index);
                intent.putExtra("imgDirAbsolutePath", dirAbsolutePath);
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
        dirAbsolutePath = intent.getStringExtra("imgDirAbsolutePath");

        Log.d(TAG, "initData: images + position===" + index + images.toString() + "\n");
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
        adapter = new RecyclerGalleryAdapter(this, images, dirAbsolutePath);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 从网络url获取路径
     * @param position
     */
    private void setImgFromUrl(int position) {
        Glide.with(RecyclerGalleryActivity.this)
                .load(images.get(position))
                .error(R.mipmap.turn_right)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1)
                .into(img);
    }

    /**
     * 从本地获取路径
     * @param position
     */
    private void setImgFromDir(int position) {
        Glide.with(RecyclerGalleryActivity.this)
                .load(new File(dirAbsolutePath + "/" +  images.get(position)))
                .error(R.mipmap.turn_right)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1)
                .into(img);
    }

}
