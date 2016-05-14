package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.TnGouImageList;
import com.buaa.yyg.baidupager.domain.Tngou;
import com.buaa.yyg.baidupager.global.TnGou;
import com.buaa.yyg.baidupager.recyclerview.BottomRecyclerOnScrollListener;
import com.buaa.yyg.baidupager.recyclerview.HomeRecyclerRefreshHeaderAdapter;
import com.buaa.yyg.baidupager.recyclerview.SpacesItemDecoration;
import com.buaa.yyg.baidupager.utils.UIUtils;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yyg on 2016/5/10.
 */
public class HomeImageActivity extends BaseActivity {

    private static final String TAG = "HomeImageActivity";
    private static final int SHOW_IMAGES_URL_MORE = 0;
    private static final int SHOW_IMAGES_URL_TOP = 1;
    private int index;
    private List<Tngou> imageUrl; // 图片集合
    private RecyclerView recyclerView;
    private HomeRecyclerRefreshHeaderAdapter adapter;
    private TextView tv_search_text;
    private TextView tv_change_other;
    private MyApiListEndpointInterface apiService;
    private SwipeRefreshLayout swipe_refresh_showimage;
    private static boolean flag = false;
    public int lastVisibleItemPosition;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_IMAGES_URL_MORE:
                    //取得图片集合,添加到集合尾部
                    if (adapter != null) {
                        adapter.addMoreItem(imageUrl, lastVisibleItemPosition);
                        Log.d(TAG, "adapter: " + adapter.toString());
                    }
                    DelayCloseSwipeRefresh(1500);
                    break;
                case SHOW_IMAGES_URL_TOP:
                    //取得图片集合,添加到集合顶部
                    if (adapter != null) {
                        adapter.addTopItem(imageUrl);
                        Log.d(TAG, "adapter: " + adapter.toString());
                    }
                    flag = false;
                    DelayCloseSwipeRefresh(1500);
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public void initView() {
        setContentView(R.layout.activity_showimage);

        //找到recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_show_image);
        swipe_refresh_showimage = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_showimage);
        tv_search_text = (TextView) findViewById(R.id.tv_search_text);
        tv_change_other = (TextView) findViewById(R.id.tv_change_other);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        tv_search_text.setText(getTextFromIndex(index));

        //text传递过来之前已经做过判断
        getJsonObjectUseRetrofit();

        setRecyclerData();

        setSwipeRefreshLayout();


    }

    @Override
    public void initListener() {

        //点击刷新实现的下拉刷新
        tv_change_other.setOnClickListener(this);

        //swipe_refresh实现的下拉刷新
        swipe_refresh_showimage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: 刷新中。。。");

                //设置下拉刷新动作
                swipe_refresh();
            }
        });

        //recyclerView的点击侦听，单击和双击
        adapter.setOnClickListener(new HomeRecyclerRefreshHeaderAdapter.onItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                Intent intent = new Intent(HomeImageActivity.this, GalleryActivity.class);
                intent.putStringArrayListExtra("images", adapter.getAllImage());
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void ItemLongClickListener(View view, int position) {
                //长按删除
                //imageUrl.size()是固定的，所以不能在这里直接remove，需要在adapter中remove
                adapter.removeItem(position);
            }
        });

        //recyclerView滑动监听，用于判断是否到底部，作用于上拉刷新
        recyclerView.addOnScrollListener(new BottomRecyclerOnScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onBottomLoadMore(int lastVisibleItemPosition) {
                getJsonObjectUseRetrofit();
                //显示swipe自带的刷新栏
                swipe_refresh_showimage.setRefreshing(true);

                HomeImageActivity.this.lastVisibleItemPosition = lastVisibleItemPosition;
            }
        });
    }

    @Override
    public void progress(View v) {
        switch (v.getId()) {
            case R.id.tv_change_other:
                //显示刷新进度条
                swipe_refresh_showimage.setRefreshing(true);
                //添加数据
                swipe_refresh();
                break;
            default:
                break;
        }
    }

    public String getTextFromIndex(int index) {
        String text = null;
        switch (index) {
            case 0:
                text = "最新美女";
                break;
            case 1:
                text = "性感美女";
                break;
            case 2:
                text = "韩日美女";
                break;
            case 3:
                text = "丝袜美腿";
                break;
            case 4:
                text = "美女照片";
                break;
            case 5:
                text = "美女写真";
                break;
            case 6:
                text = "清纯美女";
                break;
            case 7:
                text = "性感车模";
                break;
            default:
                break;
        }
        return text;
    }

    /**
     * 设置下拉刷新动作
     */
    private void swipe_refresh() {

        //在请求数据回来后使集合添加到顶部
        flag = true;
        getJsonObjectUseRetrofit();

    }

    /**
     * 设置SwipeRefreshLayout
     */
    private void setSwipeRefreshLayout() {
        //设置刷新时动画的颜色，可以设置4个
        swipe_refresh_showimage.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipe_refresh_showimage.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        //调整进度条距离屏幕顶部的距离
        swipe_refresh_showimage.setProgressViewOffset(false, 0, (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        //设置第一次进入显示进度条
        swipe_refresh_showimage.setRefreshing(true);
    }

    /**
     * 设置刷新进度条
     */
    private void DelayCloseSwipeRefresh(int millisecond) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               swipe_refresh_showimage.setRefreshing(false);
            }
        }, millisecond);
    }

    /**
     * 设置recycleData,注意adapter设置在handler中
     */
    private void setRecyclerData() {

        // setLayoutManager() 方法要在setAdapter()方法之前调用。不然会出问题
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
        recyclerView.addItemDecoration(decoration);

        // 设置recycler中的adapter
        adapter = new HomeRecyclerRefreshHeaderAdapter(HomeImageActivity.this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 根据index获得图片列表
     * http://www.tngou.net/tnfs/api/list?id=2&page=2&rows=20
     */
    private void getJsonObjectUseRetrofit() {

        //图片列表
        if (index > 0) {

            //第二次运行(换一组功能)时不需要重新创建
            if (apiService == null) {

                String BASE_URL = "http://www.tngou.net/tnfs/api/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiService = retrofit.create(MyApiListEndpointInterface.class);
            }
            int random = new Random().nextInt(3) + 1;
            Log.d(TAG, "random: " + random);
            Call<TnGouImageList> apiResponse = apiService.getImage(index, random, TnGou.LIST_ROWS);
            apiResponse.enqueue(new Callback<TnGouImageList>() {
                @Override
                public void onResponse(Call<TnGouImageList > call, Response<TnGouImageList > response) {
                    if (response.isSuccessful()) {
                        TnGouImageList imageList = response.body();
                        Log.d(TAG, "onResponse: 传过来的index :  " + index);
                        if (imageList != null) {
                            //请求成功
                            Log.d(TAG, "onResponse: " + "请求成功");
                            Log.d(TAG, "imageList.getTotal(): " + imageList.getTotal());
                            Log.d(TAG, "imageList: " + imageList);
                            Log.d(TAG, "imageList.toString(): " + imageList.toString());

                            if (imageList.getTngou().size() != 0) {
                                //成功返回内容
                                imageUrl = imageList.getTngou();

                                handler.sendEmptyMessage(SHOW_IMAGES_URL_MORE);
                                if (flag) {
                                    handler.sendEmptyMessage(SHOW_IMAGES_URL_TOP);
                                }

                                Log.d(TAG, "getContentUrl: " + imageList.getTngou().get(2).getImg());
                                Log.d(TAG, "onResponse: imagesUrl = " + imageUrl.size());
                            } else {
                                UIUtils.showToast(HomeImageActivity.this, "获取数据失败");
                                finish();
                            }
                        }
                    }

                    Log.e(TAG, "onResponse: " + response.errorBody());
                }

                @Override
                public void onFailure(Call<TnGouImageList > call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage() );
                    UIUtils.showToast(HomeImageActivity.this, "网络错误，请稍后再试");
                    swipe_refresh_showimage.setRefreshing(false);
                    //第一次请求失败退出activity
                    if (imageUrl == null) {
                        finish();
                    }
                }
            });
        } else if(index == 0) {
            //最新图片

        }
    }

    //list?id=2&page=2&rows=20
    public interface MyApiListEndpointInterface {
        @GET("list")
        Call<TnGouImageList> getImage(@Query("id") int id,
                                      @Query("page") int page,
                                      @Query("rows") int rows);
    }

}


