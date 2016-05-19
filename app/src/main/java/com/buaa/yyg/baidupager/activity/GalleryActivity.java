package com.buaa.yyg.baidupager.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Viewpager画廊效果
 * Created by yyg on 2016/4/27.
 */
public class GalleryActivity extends Activity implements View.OnClickListener{

    private static final int SET_CURRENT_ITEM = 0;
    private static final int SET_WALL_PAGER = 1;
    private static final int DOWNLOAD_IMAGE = 2;
    private ArrayList<String> images = new ArrayList<>();
    private int index;
    Bitmap bmp = null;
    private LinearLayout ll_tv_setwallpager;
    private static final String TAG = "GalleryActivity";
    //画廊
    private ViewPager myFullViewPager;
    private String dirAbsolutePath;
    private LinearLayout ll_download_image;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SET_CURRENT_ITEM:
                    myFullViewPager.setCurrentItem(index);
                    break;
                case SET_WALL_PAGER:
                    setWallPage(bmp);
                    break;
                case DOWNLOAD_IMAGE:
                    downloadImage();
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
        //设置壁纸
        ll_tv_setwallpager = (LinearLayout) findViewById(R.id.ll_tv_setwallpager);
        ll_download_image = (LinearLayout) findViewById(R.id.ll_download_image);
    }

    private void initData() {
        //获取传递过来的数据
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        index = intent.getIntExtra("position", 0);
        dirAbsolutePath = intent.getStringExtra("imgDirAbsolutePath");

        Log.d(TAG, "initData: images + position===" + index + images.toString());

        //更改当前显示位置
        handler.sendEmptyMessage(SET_CURRENT_ITEM);
        myFullViewPager.setAdapter(new MyAdapter());
    }

    private void initListener() {
        ll_tv_setwallpager.setOnClickListener(this);
        ll_download_image.setOnClickListener(this);

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
                //点击设置壁纸
                if (TextUtils.isEmpty(dirAbsolutePath)) {
                    //文件夹路径为空，下载图片到bitmap中
                    getURLImage(images.get( getCurrentItem()), SET_WALL_PAGER);
                } else {
                    getLocalImage(dirAbsolutePath + "/" +  images.get( getCurrentItem() ));
                }
                UIUtils.showToast(this, "壁纸设置成功");
                break;

            case R.id.ll_download_image:
                //点击下载
                if (TextUtils.isEmpty(dirAbsolutePath)) {
                    //文件路径为空，是网络图片
                    //判断内部存储卡存不存在,如果不存在，无法读写
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        UIUtils.showToast(GalleryActivity.this, "内部存储卡不存在");
                        return;
                    }
                    //先下载图片到bitmap中
                    getURLImage(images.get( getCurrentItem()), DOWNLOAD_IMAGE);
                } else {
                    //是本地图片
                    UIUtils.showToast(GalleryActivity.this, "已经是本地图片，无需下载");
                }
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
     * 下载壁纸到本地
     */
    private void downloadImage() {
        try {
            File dirFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DDWallPager/images/");
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DDWallPager/images/";
            String fileName = UUID.randomUUID().toString();
            File file = new File(dir + fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            UIUtils.showToast(GalleryActivity.this, "下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置壁纸,在handler中调用
     */
    private void setWallPage(Bitmap bitmap) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击设置壁纸，下载图片到bitmap中，通过handler通知下载好了
     * @param url
     */
    public void getURLImage(final String url, final int type) {
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
                        message.what = type;
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
     * 设置本地图片为壁纸
     */
    private void getLocalImage(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        setWallPage(bitmap);
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

            if (TextUtils.isEmpty(dirAbsolutePath)) {
                //文件夹路径为空，加载网络图片
                setImgFromUrl(position, imageView);
            } else {
                setImgFromDir(position, imageView);
            }
            return imageView;
        }

        /**
         * 从网络url获取路径
         * @param position
         */
        private void setImgFromUrl(int position, ImageView imageView) {
            Glide.with(GalleryActivity.this)
                    .load(images.get(position))
                    //                    .placeholder(R.mipmap.turn_right)
                    .error(R.mipmap.turn_right)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(1)
                    .into(imageView);
        }

        /**
         * 从本地获取路径
         * @param position
         */
        private void setImgFromDir(int position, ImageView imageView) {
            Glide.with(GalleryActivity.this)
                    .load(new File(dirAbsolutePath + "/" +  images.get(position)))
                    //                    .placeholder(R.mipmap.turn_right)
                    .error(R.mipmap.turn_right)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(1)
                    .into(imageView);
        }
    }
}
