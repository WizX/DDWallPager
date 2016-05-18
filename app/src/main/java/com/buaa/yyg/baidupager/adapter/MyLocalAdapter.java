package com.buaa.yyg.baidupager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.utils.CommonAdapter;
import com.buaa.yyg.baidupager.utils.ViewHolder;

import java.util.List;

/**
 * Created by yyg on 2016/5/18.
 */
public class MyLocalAdapter extends CommonAdapter<String> {

    /**
     * 文件夹路径
     */
    private String mDirPath;

    public MyLocalAdapter(Context context, List<String> mDatas, int itemLayoutId,
                          String dirPath) {
        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
    }


    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);

        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);

        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                mImageView.setColorFilter(Color.parseColor("#77000000"));

                //点击后跳转到RecyclerGalleryActivity
                mImageViewClickListener.intent2RecyclerGallery(position);

                //500毫秒之后恢复点击前的颜色
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setColorFilter(null);
                    }
                }, 500);
            }
        });
    }

    public interface OnImageViewClickListener {
        void intent2RecyclerGallery(int position);
    }

    private OnImageViewClickListener mImageViewClickListener;

    public void setImageViewClickListener(OnImageViewClickListener mImageViewClickListener) {
        this.mImageViewClickListener = mImageViewClickListener;
    }

}
