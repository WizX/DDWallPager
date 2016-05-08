package com.buaa.yyg.baidupager.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.buaa.yyg.baidupager.global.Constant;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by yyg on 2016/5/8.
 */
public class HttpURLConnectionUtils {

    /**
     * 连接服务器
     */
    public static void conn(final Activity context, final String urlPath, final String name, final List<String> imagesUrl) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream(); //json文本
                        String json = StreamUtils.readFromStream(is);
                        if (!TextUtils.isEmpty(json)) {
                            JSONTokener jsonTokener = new JSONTokener(json);
                            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                            JSONObject jsonObj = (JSONObject) jsonTokener.nextValue();
                            JSONArray array = jsonObj.getJSONArray(name);
                            for (int i = 0; i < array.length(); i++) {
                                imagesUrl.add(Constant.URL + array.getJSONObject(i).getString("pager"));
                                Log.d("123", Constant.URL + array.getJSONObject(i).getString("pager"));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //错误2011，网络错误，请联系客服
                    UIUtils.showToast(context, "错误2011，网络错误，请联系客服");
                }
            }
        }).start();
    }
}
