package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.graphics.Rect;
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
import com.buaa.yyg.baidupager.domain.APIImage;
import com.buaa.yyg.baidupager.domain.Value;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.utils.UIUtils;
import com.buaa.yyg.baidupager.view.MySearchImageAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
public class ShowImageActivity extends BaseActivity {

    private static final String TAG = "ShowImageActivity";
    private static final int SHOW_IMAGES_URL_MORE = 0;
    private static final int SHOW_IMAGES_URL_TOP = 1;
    private String text;
    private List<Value> imageUrl = new ArrayList<>(); // 图片集合
    private RecyclerView recyclerView;
    private MySearchImageAdapter adapter;
    private TextView tv_search_text;
    private TextView tv_change_other;
    private MyApiTypeEndpointInterface apiService;
    private SwipeRefreshLayout swipe_refresh_showimage;
    private static boolean Tag = false;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_IMAGES_URL_MORE:
                    //取得图片集合,添加到集合尾部
                    if (adapter != null) {
                        adapter.addMoreItem(imageUrl);
                    }
                    break;
                case SHOW_IMAGES_URL_TOP:
                    //取得图片集合,添加到集合顶部
                    if (adapter != null) {
                        adapter.addTopItem(imageUrl);
                    }
                    Tag = false;
                    break;
                default:
                    break;
            }
            return false;
        }
    });

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
        text = intent.getStringExtra("text");
        tv_search_text.setText(text);

        //text传递过来之前已经做过判断
        getJsonObjectUseRetrofit();

        setRecyclerData();

        setSwipeRefreshLayout();


    }

    @Override
    public void initListener() {
        tv_change_other.setOnClickListener(this);

        //下拉刷新
        swipe_refresh_showimage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: 刷新中。。。");

                //在请求数据回来后使集合添加到顶部
                Tag = true;
                getJsonObjectUseRetrofit();

                //进度条刷新3秒
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refresh_showimage.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        adapter.setOnClickListener(new MySearchImageAdapter.onItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                //单击
                UIUtils.showToast(ShowImageActivity.this, "点击了" + position);

            }

            @Override
            public void ItemLongClickListener(View view, int position) {
                //长按删除
                imageUrl.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }

    @Override
    public void progress(View v) {
        switch (v.getId()) {
            case R.id.tv_change_other:
                getJsonObjectUseRetrofit();
                break;
            default:
                break;
        }
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

    }

    /**
     * 设置recycleData,注意adapter设置在handler中
     */
    private void setRecyclerData() {
        // 得到图片集合
        // 设置recycler中的adapter
        adapter = new MySearchImageAdapter(ShowImageActivity.this);
        Log.d(TAG, "adapter: " + adapter.toString());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
        recyclerView.addItemDecoration(decoration);

    }

    /**
     * 根据text获得图片列表
     * https://bingapis.azure-api.net/api/v5/images/search[?q][&count][&offset][&mkt][&safeSearch]
     * https://bingapis.azure-api.net/api/v5/images/search?q=美女&count=10&offset=0&mkt=zh-CN
     * Headers:5cf2bca526bc48308659bb75e384377c
     */
    private void getJsonObjectUseRetrofit() {

        //第二次运行(换一组功能)时不需要重新创建
        if (apiService == null) {
            //定义拦截器，添加headers
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("Ocp-Apim-Subscription-Key", Constant.HEADERS_SEARCH).build();
                    return chain.proceed(newRequest);
                }
            };

            //添加拦截器到okhttpclient
            OkHttpClient client =  new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .build();

            String BASE_URL = "https://bingapis.azure-api.net/api/v5/images/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiService = retrofit.create(MyApiTypeEndpointInterface.class);
        }

        Call<APIImage> apiResponse = apiService.getImage(text, Constant.COUNT_SEARCH, new Random().nextInt(100) + 1, Constant.ZH_CN);
        apiResponse.enqueue(new Callback<APIImage >() {
            @Override
            public void onResponse(Call<APIImage > call, Response<APIImage > response) {
                if (response.isSuccessful()) {
                    APIImage imageAPI = response.body();
                    Log.d(TAG, "onResponse: 传过来的text :  " + text);
                    if (imageAPI != null) {
                        //请求成功
                        Log.d(TAG, "onResponse: " + "请求成功");
                       
                        if (imageAPI.getValue().size() != 0) {
                            //成功返回内容
                            imageUrl = imageAPI.getValue();
                            handler.sendEmptyMessage(SHOW_IMAGES_URL_MORE);
                            if (Tag) {
                                handler.sendEmptyMessage(SHOW_IMAGES_URL_TOP);
                            }

                            Log.d(TAG, "getContentUrl: " + imageAPI.getValue().get(2).getContentUrl());
                            Log.d(TAG, "onResponse: imagesUrl = " + imageUrl.size());
                        } else {
                            UIUtils.showToast(ShowImageActivity.this, "内容不存在，请重新输入");
                            finish();
                        }
                    }
                }

//                Log.e(TAG, "onResponse: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<APIImage > call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
                UIUtils.showToast(ShowImageActivity.this, "网络错误，请稍后再试");
                finish();
            }
        });
    }

    //search?q=美女&count=10&offset=0&mkt=zh-CN
    public interface MyApiTypeEndpointInterface {
        @GET("search")
        Call<APIImage> getImage(@Query("q") String q,
                                @Query("count") int count,
                                @Query("offset") int offset,
                                @Query("mkt") String mkt);
    }

}


class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private StaggeredGridLayoutManager.LayoutParams lp;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        lp = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();

        if (position == 0 || position==1){
            outRect.top = space * 2;
        } else {
            outRect.top = space;
        }

        if (lp.getSpanIndex() == 0) {
            outRect.left = space * 2;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = space * 2;
        }
        outRect.bottom = space;

            /*    //注掉的方法总有漏网之鱼，不知为啥
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (position == 0 || position == 1) {
            //用于设置第一行跟顶部的距离
            outRect.top = space;
        }

        if (lp.getSpanIndex() == 0) {
            //用于设同行两个间隔间跟其距离左右屏幕间隔相同
            outRect.right = 0;
        }*/
    }
}


