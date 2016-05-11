package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.Contentlist;
import com.buaa.yyg.baidupager.domain.ImageAPI;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.utils.UIUtils;
import com.buaa.yyg.baidupager.view.MySearchImageAdapter;

import java.util.ArrayList;
import java.util.List;

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
    private static final int SHOW_IMAGES_URL = 1;
    private String type;
    private List<Contentlist> imageUrl = new ArrayList<>(); // 图片集合
    private RecyclerView recyclerView;
    private MySearchImageAdapter adapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_IMAGES_URL:

                    // 得到图片集合
                    // 设置recycler中的adapter
                    adapter = new MySearchImageAdapter(ShowImageActivity.this, imageUrl);
                    recyclerView.setAdapter(adapter);
                    //设置item之间的间隔
                    SpacesItemDecoration decoration = new SpacesItemDecoration(8);
                    recyclerView.addItemDecoration(decoration);

                    //对item进行点击监听
                    recyclerItemListener();

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
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (!TextUtils.isEmpty(type)) {
            getJsonObjectUseRetrofitFromType(type);
        }

        setRecyclerData();
    }

    /**
     * 设置recycleData,注意adapter设置在handler中
     */
    private void setRecyclerData() {
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //在handler部分设置adapter

    }

    private void recyclerItemListener() {
        adapter.setOnClickListener(new MySearchImageAdapter.onItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                UIUtils.showToast(ShowImageActivity.this, "点击了" + position);
                String id = imageUrl.get(position).getId();
                //把获得的imageIdUrl传递给画廊activity


            }

            @Override
            public void ItemLongClickListener(View view, int position) {
                //长按删除
                imageUrl.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }

    /**
     * 根据type获得分类信息
     * 此时url是"http://route.showapi.com/959-1?showapi_appid=myappid&type=&showapi_sign=mysecret"
     * @param type
     */
    private void getJsonObjectUseRetrofitFromType(final String type) {
        String BASE_URL = "http://route.showapi.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiTypeEndpointInterface apiService = retrofit.create(MyApiTypeEndpointInterface.class);
        Call<ImageAPI> apiResponse = apiService.getImage(Constant.myAppid, type, Constant.mySecret);
        apiResponse.enqueue(new Callback<ImageAPI >() {
            @Override
            public void onResponse(Call<ImageAPI > call, Response<ImageAPI > response) {
                if (response.isSuccessful()) {
                    ImageAPI imageAPI = response.body();
                    Log.d(TAG, "onResponse: type" + type);
                    if (imageAPI != null) {
                        //请求成功
                        Log.d(TAG, "onResponse: " + "请求成功");
                        Log.d(TAG, "onResponse: " + imageAPI.getShowapiResBody().getRetCode());
                        if (imageAPI.getShowapiResBody().getRetCode() == 0) {
                            //返回正确的json数据，如果为-1表示失败
                            imageUrl = imageAPI.getShowapiResBody().getPagebean().getContentlist();
                            handler.sendEmptyMessage(SHOW_IMAGES_URL);

                            Log.d(TAG, "onResponse: imagesUrl = " + imageUrl.size());
                        }

                    }
                }

//                Log.e(TAG, "onResponse: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<ImageAPI > call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    public interface MyApiTypeEndpointInterface {
        @GET("959-1")
        Call<ImageAPI> getImage(@Query("showapi_appid") String showapiAppid,
                                @Query("type") String type,
                                @Query("showapi_sign") String showapiSign);
    }


    @Override
    public void initListener() {
    }

    @Override
    public void progress() {
    }

//    //根据id获得详细图集信息
//    //此时url是"http://route.showapi.com/959-2?showapi_appid=myappid&id=&showapi_sign=mysecret"
//    private void getJsonObjectUseRetrofitFromId(final String id) {
//        String BASE_URL = "http://route.showapi.com/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MyApiTypeEndpointInterface apiService = retrofit.create(MyApiTypeEndpointInterface.class);
//        Call<ImageAPI> apiResponse = apiService.getImage(Constant.myAppid, type, Constant.mySecret);
//        apiResponse.enqueue(new Callback<ImageAPI >() {
//            @Override
//            public void onResponse(Call<ImageAPI > call, Response<ImageAPI > response) {
//                if (response.isSuccessful()) {
//                    ImageAPI imageAPI = response.body();
//                    Log.d(TAG, "onResponse: type" + type);
//                    if (imageAPI != null) {
//                        //请求成功
//                        Log.d(TAG, "onResponse: " + "请求成功");
//                        Log.d(TAG, "onResponse: " + imageAPI.getShowapiResBody().getRetCode());
//                        if (imageAPI.getShowapiResBody().getRetCode() == 0) {
//                            //返回正确的json数据，如果为-1表示失败
//                            imageUrl = imageAPI.getShowapiResBody().getPagebean().getContentlist();
//                            handler.sendEmptyMessage(SHOW_IMAGES_URL);
//
//                            Log.d(TAG, "onResponse: imagesUrl = " + imageUrl.size());
//                        }
//
//                    }
//                }
//
//                //                Log.e(TAG, "onResponse: " + response.errorBody());
//            }
//
//            @Override
//            public void onFailure(Call<ImageAPI > call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage() );
//            }
//        });
//    }
//
//    public interface MyApiIdEndpointInterface {
//        @GET("959-1")
//        Call<ImageAPI> getImage(@Query("showapi_appid") String showapiAppid,
//                                @Query("type") String type,
//                                @Query("showapi_sign") String showapiSign);
//    }

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

    }
}
