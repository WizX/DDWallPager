package com.buaa.yyg.baidupager.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.ImageAPI;
import com.buaa.yyg.baidupager.global.Constant;

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
public class ShowImageActivity extends BaseActivity{

    private static final String TAG = "ShowImageActivity";
    private Intent intent;
    private String type;
    private TextView tv;

    @Override
    public void initView() {
        setContentView(R.layout.activity_showimage);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

//        OkHttpClient client = new OkHttpClient();
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                okhttp3.Response response = chain.proceed(chain.request());
//                // Do anything with response here
//                return response;
//            }
//        });

        //"http://route.showapi.com/959-1?showapi_appid=myappid&type=&showapi_sign=mysecret"
        String BASE_URL = "http://route.showapi.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
        Call<ImageAPI> apiResponse = apiService.getImage(Constant.myAppid, type, Constant.mySecret);
        apiResponse.enqueue(new Callback<ImageAPI>() {
            @Override
            public void onResponse(Call<ImageAPI> call, Response<ImageAPI> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                Log.e(TAG, "onResponse: " + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<ImageAPI> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });

    }



    @Override
    public void initListener() {

    }

    @Override
    public void progress() {

    }

    public interface MyApiEndpointInterface {
        @GET("959-1")
        Call<ImageAPI> getImage(@Query("showapi_appid") String showapiAppid,
                               @Query("type") String type,
                               @Query("showapi_sign") String showapiSign);
    }
}
