package com.buaa.yyg.baidupager.fragment;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.utils.StreamUtils;
import com.buaa.yyg.baidupager.utils.UIUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 设置
 * Created by yyg on 2016/4/22.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private View view;
    private RelativeLayout tv_check_version;
    private static final int SHOW_UPDATE_DIALOG = 1;
    private int versionCodeClient;
    private String downloadurl;
    private int versionCodeService;
    private String desc;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_UPDATE_DIALOG:
                    //因为对话框是activity的一部分，显示对话框必须指定activity的环境（令牌）
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    private String versionNameClient;
    private TextView tv_version_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        findView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void findView() {
        tv_check_version = (RelativeLayout) view.findViewById(R.id.tv_check_version);
        tv_version_name = (TextView) view.findViewById(R.id.tv_version_name);
    }

    private void init() {
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
                            UIUtils.showToast(getActivity(), "错误2013，服务器json获取失败，请联系客服");
                        } else {
                            JSONObject jsonObj = new JSONObject(json);
                            versionCodeService = jsonObj.getInt("version");
                            downloadurl = jsonObj.getString("downloadurl");
                            desc = jsonObj.getString("desc");
                            if (versionCodeService == versionCodeClient) {
                                //服务器版本和本地版本一致,吐司 当前已是最新版本
                                Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_LONG).show();
                            } else {
                                //不同，弹出更新提醒对话框
                                msg.what = SHOW_UPDATE_DIALOG;
                                handler.sendMessage(msg);
                            }
                        }
                    } else {
                        //服务器状态码错误
                        UIUtils.showToast(getActivity(), "错误2014，服务器状态码错误，请联系客服");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //错误
                    UIUtils.showToast(getActivity(), "网络错误");
                }
            }
        }).start();
    }

    /**
     * 获取包管理器
     * @return
     */
    public PackageInfo getPackageInfo() {
        try {
            PackageManager packManager = getActivity().getPackageManager();
            PackageInfo packInfo = packManager.getPackageInfo(getActivity().getPackageName(), 0);
            return packInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //不会发生   can't reach
            return null;
        }
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
