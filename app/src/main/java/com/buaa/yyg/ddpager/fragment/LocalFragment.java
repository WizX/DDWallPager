package com.buaa.yyg.ddpager.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buaa.yyg.ddpager.R;
import com.buaa.yyg.ddpager.activity.ListImageDirPopupWindow;
import com.buaa.yyg.ddpager.activity.RecyclerGalleryActivity;
import com.buaa.yyg.ddpager.adapter.MyLocalAdapter;
import com.buaa.yyg.ddpager.domain.ImageFloder;
import com.buaa.yyg.ddpager.utils.UIUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * 本地
 * Created by yyg on 2016/4/22.
 */
public class LocalFragment extends Fragment {

    private static final String TAG = "LocalFragment";
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;

    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;

    /**
     * 所有的图片
     */
    private ArrayList<String> mImgs;

    private GridView mGridView;
    private MyLocalAdapter mAdapter;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private int mScreenHeight;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private static final int SEARCH_IMAGES_FINISH = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH_IMAGES_FINISH:
                    mProgressDialog.dismiss();
                    //为view绑定数据
                    data2View();
                    //初始化展示文件夹的popupWindow
                    initListDirPopupWindow();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_fragment, container, false);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        initView(view);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getImages();
        initEvent();

    }

    private void initView(View view) {
        mGridView = (GridView) view.findViewById(R.id.id_gridView);
        mChooseDir = (TextView) view.findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) view.findViewById(R.id.id_total_count);
        mBottomLy = (RelativeLayout) view.findViewById(R.id.id_bottom_ly);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法运行在子线程中完成图片的扫描，最多获得图片最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //无外部存储设备
            UIUtils.showToast(getActivity(), "未发现外部存储设备");
            return;
        }
        //显示进度条
        mProgressDialog = ProgressDialog.show(getActivity(), null, "正在加载...");
        //开启子线程扫描图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getActivity().getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] {"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
//                Log.e(TAG, mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                    Log.d(TAG, path);
                    //拿到第一张图片的路径
                    if (firstImage == null) {
                        firstImage = path;
                    }
                    //获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    //利用一个HashSet防止多次扫描同一个文件夹（不加此判断的话图片多起来就废了）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        //初始化iamgeFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    //有些图片比较诡异，需添加此判断
                    if (parentFile.list() == null) {
                        continue;
                    }

                    //获取文件夹内图片数量
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override  //添加过滤器
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg")) {
                                return true;
                            }
                            return false;
                        }
                    }).length;

                    //所有文件夹里的所有图片
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    //获取图片最多文件夹路径 mImgDir
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                //扫描完成，辅助的HashSet也就可以释放内存了
                //fragment中不能置为null，不然会报空指针
//                mDirPaths = null;

                //通知handler扫描图片完成
                mHandler.sendEmptyMessage(SEARCH_IMAGES_FINISH);
            }
        }).start();

    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                //设置背景颜色变暗
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.3f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }


    /**
     * 初始化展示文件夹的popupWindow
     */
    private void initListDirPopupWindow() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, (int)(mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getActivity()).inflate(R.layout.list_dir, null));
        mListImageDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景颜色变暗
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //设置文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(new ListImageDirPopupWindow.OnImageDirSelected() {
            @Override
            public void selected(ImageFloder floder) {
                mImgDir = new File(floder.getDir());
                mImgs = new ArrayList<String>(Arrays.asList(mImgDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg") || filename.endsWith(".png")
                                || filename.endsWith(".jpeg")) {
                            return true;
                        }
                        return false;
                    }
                })));
                mAdapter = new MyLocalAdapter(getActivity(), mImgs, R.layout.local_grid_item, mImgDir.getAbsolutePath());

                //条目点击事件，跳转到RecyclerGallery
                intent2GalleryActivity();

                mGridView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
                mImageCount.setText(floder.getCount() + "张");
                mChooseDir.setText(floder.getName());
                mListImageDirPopupWindow.dismiss();
            }
        });
    }

    /**
     * 为view绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            UIUtils.showToast(getActivity(), "一张图片都没有扫描到");
            return;
        }
        mImgs = new ArrayList<String>(Arrays.asList(mImgDir.list()));
        //可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗
        mAdapter = new MyLocalAdapter(getActivity(), mImgs, R.layout.local_grid_item, mImgDir.getAbsolutePath());

        //条目点击事件，跳转到RecyclerGallery
        intent2GalleryActivity();

        mGridView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    /**
     * 跳转到GalleryActivity
     */
    private void intent2GalleryActivity() {
        mAdapter.setImageViewClickListener(new MyLocalAdapter.OnImageViewClickListener() {
            @Override
            public void intent2RecyclerGallery(int position) {
                Intent intent = new Intent(getActivity(), RecyclerGalleryActivity.class);
                intent.putStringArrayListExtra("images", mImgs);
                intent.putExtra("position", position);
                intent.putExtra("imgDirAbsolutePath", mImgDir.getAbsolutePath());
                startActivity(intent);
            }
        });
    }
}
