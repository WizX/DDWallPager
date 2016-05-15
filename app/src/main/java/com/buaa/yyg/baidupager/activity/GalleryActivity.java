package com.buaa.yyg.baidupager.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Viewpager画廊效果
 * Created by yyg on 2016/4/27.
 */
public class GalleryActivity extends Activity implements View.OnClickListener{

    private static final int SET_CURRENT_ITEM = 0;
    private static final int SET_BITMAP_RESOURCE = 1;
    private ArrayList<String> images = new ArrayList<>();
    private int index;
    Bitmap bmp = null;
    private LinearLayout ll_tv_setwallpager;
    private static final String TAG = "GalleryActivity";
    //画廊
    private ViewPager myFullViewPager;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SET_CURRENT_ITEM:
                    myFullViewPager.setCurrentItem(index);
                    break;
                case SET_BITMAP_RESOURCE:
                    setWallPage();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gallery);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        //初始化viewpager
        myFullViewPager = (ViewPager) findViewById(R.id.myFullViewPager);
        ll_tv_setwallpager = (LinearLayout) findViewById(R.id.ll_tv_setwallpager);
    }

    private void initData() {
        //获取传递过来的数据
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        index = intent.getIntExtra("position", 0);

        Log.d(TAG, "initData: images + position===" + index + images.toString());

        //更改当前显示位置
        handler.sendEmptyMessage(SET_CURRENT_ITEM);
        myFullViewPager.setAdapter(new MyAdapter());
    }

    private void initListener() {
        ll_tv_setwallpager.setOnClickListener(this);
        myFullViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state ==1的时辰默示正在滑动，state==2的时辰默示滑动完毕了，state==0的时辰默示什么都没做。
            }
        });
    }

    @Override
    protected void onDestroy() {
        images.clear();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tv_setwallpager:
                //点击设置壁纸，下载图片到bitmap中
                getURLImage(images.get( getCurrentItem() ));
                UIUtils.showToast(this, "壁纸设置成功");
                break;
            default:
                break;
        }
    }

    /**
     * 获取当前item,即position
     * @return
     */
    private int getCurrentItem() {
        return myFullViewPager.getCurrentItem();
    }

    /**
     * 设置壁纸,在handler中调用
     */
    private void setWallPage() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击设置壁纸，下载图片到bitmap中，通过handler通知下载好了
     * @param url
     */
    public void getURLImage(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                try {
                    URL myUrl = new URL(url);
                    // 获得连接
                    HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setUseCaches(false);//不缓存
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if(code == 200) {
                        InputStream is = conn.getInputStream();//获得图片的数据流
                        bmp = BitmapFactory.decodeStream(is);
                        message.what = SET_BITMAP_RESOURCE;
                        handler.sendMessage(message);
                        is.close();
                    } else {
                        UIUtils.showToast(GalleryActivity.this, "网络错误");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * PagerAdapter
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (images != null) {
                return images.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(GalleryActivity.this);
            imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);

            Glide.with(GalleryActivity.this)
                    .load(images.get(position))
//                    .placeholder(R.mipmap.turn_right)
                    .error(R.mipmap.turn_right)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(1)
                    .into(imageView);
            return imageView;
        }
    }
}
