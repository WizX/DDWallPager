package com.buaa.yyg.baidupager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by yyg on 2016/5/10.
 */
public class ApiActivity extends AppCompatActivity{

    //以下代码仅为演示用,具体传入参数请参看接口描述详情页.
    //需要引用android-async-http库(sdk中已经包括此jar包) ,其项目地址为： http://loopj.com/android-async-http/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_api);
        final TextView txt = (TextView) this.findViewById(R.id.textView1);
        Button myBtn = (Button) this.findViewById(R.id.button1);
        final AsyncHttpResponseHandler resHandler=new AsyncHttpResponseHandler(){
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                //做一些异常处理
                e.printStackTrace();
            }
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    System.out.println("response is :"+new String(responseBody,"utf-8"));
                    txt.setText(new String(responseBody,"utf-8")+new Date());
                    //在此对返回内容做处理
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }};

        myBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ShowApiRequest("http://route.showapi.com/959-1", "18823", "ee018820898d48afab59b177ff04174f")
                        .setResponseHandler(resHandler)
                        .addTextPara("type", "mote")
                        .post();
            }
        });
    }
}
