package com.buaa.yyg.baidupager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.fragment.ChosenFragment;
import com.buaa.yyg.baidupager.fragment.HomeFragment;
import com.buaa.yyg.baidupager.fragment.LocalFragment;
import com.buaa.yyg.baidupager.fragment.SearchFragment;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.utils.StreamUtils;
import com.buaa.yyg.baidupager.utils.UIUtils;
import com.buaa.yyg.baidupager.view.MyBottomLayout;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private static final int SHOW_UPDATE_DIALOG = 1;
    private int versionCodeClient;
    private String downloadurl;
    private int versionCodeService;
    private String versionNameClient;
    private String desc;


    @Bind(R.id.myViewPager)
    ViewPager myViewPager;
    @Bind(R.id.myBottomLayout)
    MyBottomLayout myBottomLayout;
    @Bind(R.id.drawer_layout_main)
    DrawerLayout drawerLayoutMain;
    @Bind(R.id.tv_check_version)
    RelativeLayout tv_check_version;
    @Bind(R.id.tv_version_name)
    TextView tv_version_name;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_UPDATE_DIALOG:
                    //因为对话框是activity的一部分，显示对话框必须指定activity的环境（令牌）
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("更新提醒");
                    builder.setMessage(desc);
                    //                    builder.setCancelable(false);
                    builder.setPositiveButton("立刻更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //下载
                            download(Constant.URL + downloadurl);
                        }
                    });
                    builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //super必须在最后，这样先setContentView然后super到
        //BaseActivity的onCreate，之后才会调用initData等方法
        //不这样会报空指针异常
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        myViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        drawerLayoutMain.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drawerLayoutMain.getChildAt(0);
                View mMenu = drawerView;

                float scale = 1 - slideOffset;  //1 ---- 0
//                float rightScale = 0.8f + scale * 0.2f;  //1 ---- 0.8f

                float leftScale = 1 - 0.3f * scale;  //0.7f ---- 1

                ViewHelper.setScaleX(mMenu, leftScale);     //改变菜单区的缩放
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));  //属性动画，改变透明度 0.6f --- 1
                ViewHelper.setTranslationX(mContent,
                        mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);  //设置动画中心点
                ViewHelper.setPivotY(mContent,
                        mContent.getMeasuredHeight() / 2);
                mContent.invalidate();  //请求view重绘
//                ViewHelper.setScaleX(mContent, rightScale);   //改变内容区的缩放
//                ViewHelper.setScaleY(mContent, rightScale);
            }
        });

        setting();
    }

    @Override
    public void initListener() {
        //设值注入，初始化MyBottomLayout页面的回调实例
        myBottomLayout.setOnCallbackListener(new MyCallBackListener());

        //ViewPager页面监听 使用add而不是set
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //0是静止，1是正在滑动，2是停止滑动
                if (state == 2) {
                    //设置滑动ViewPager导航同步变化
                    myBottomLayout.setResidAndColor(myViewPager.getCurrentItem());
                }
            }
        });
    }

    @Override
    public void progress(View v) {

    }

    /**
     * 实现回调监听方法，用于改变当前item值
     * 在FragmentPagerAdapter的getItem方法中切换Fragment
     */
    private class MyCallBackListener implements MyBottomLayout.ICallbackListener {

        @Override
        public void click(int id) {
            switch (id) {
                case R.id.homeLayout:
                    myViewPager.setCurrentItem(0);
                    break;
                case R.id.chosenLayout:
                    myViewPager.setCurrentItem(1);
                    break;
                case R.id.searchLayout:
                    myViewPager.setCurrentItem(2);
                    break;
                case R.id.localLayout:
                    myViewPager.setCurrentItem(3);
                    break;
            }
        }
    }

    /**
     * viewPager的adapter，改变当前fragment
     */
    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ChosenFragment();
                case 2:
                    return new SearchFragment();
                case 3:
                    return new LocalFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            //一共4个页面
            return 4;
        }
    }

    private void setting() {
        versionCodeClient = getPackageInfo().versionCode;
        versionNameClient = getPackageInfo().versionName;
        tv_version_name.setText("V " + versionNameClient);
        listener();
    }

    private void listener() {
        tv_check_version.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check_version:
                checkVersion();
                Log.d("111", "检查更新");
                break;
            default:
                break;
        }
    }

    /**
     * 获取包管理器
     * @return
     */
    public PackageInfo getPackageInfo() {
        try {
            PackageManager packManager = getPackageManager();
            PackageInfo packInfo = packManager.getPackageInfo(getPackageName(), 0);
            return packInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //不会发生   can't reach
            return null;
        }
    }

    /**
     * 连接服务器，检查版本号，看是否有更新
     */
    public void checkVersion() {
        new Thread(new Runnable() {
            private int versionCodeService;

            @Override
            public void run() {
                Message msg = Message.obtain();
                try {
                    URL url = new URL(getResources().getString(R.string.url_update));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream(); //json文本
                        String json = StreamUtils.readFromStream(is);
                        if (TextUtils.isEmpty(json)) {
                            //服务器json获取失败
                            UIUtils.showToast(MainActivity.this, "错误2013，服务器json获取失败，请联系客服");
                        } else {
                            JSONObject jsonObj = new JSONObject(json);
                            versionCodeService = jsonObj.getInt("version");
                            downloadurl = jsonObj.getString("downloadurl");
                            desc = jsonObj.getString("desc");
                            if (versionCodeService == versionCodeClient) {
                                //服务器版本和本地版本一致,吐司 当前已是最新版本
                                Toast.makeText(MainActivity.this, "当前已是最新版本", Toast.LENGTH_LONG).show();
                            } else {
                                //不同，弹出更新提醒对话框
                                msg.what = SHOW_UPDATE_DIALOG;
                                handler.sendMessage(msg);
                            }
                        }
                    } else {
                        //服务器状态码错误
                        UIUtils.showToast(MainActivity.this, "错误2014，服务器状态码错误，请联系客服");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //错误
                    UIUtils.showToast(MainActivity.this, "网络错误");
                }
            }
        }).start();
    }

    /**
     * 使用xUtils3下载apk
     * @param downloadurl
     */
    private void download(final String downloadurl) {
        RequestParams params = new RequestParams(downloadurl);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath("/sdcard/ddwallpager.apk");
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功下载
                // 安装apk
                //<action android:name="android.intent.action.VIEW" />
                //<category android:name="android.intent.category.DEFAULT" />
                //<data android:scheme="content" />
                //<data android:scheme="file" />
                //<data android:mimeType="application/vnd.android.package-archive" />
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "ddwallpager.apk")),
                        "application/vnd.android.package-archive");
                startActivityForResult(intent, 0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //下载失败
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

}
