package com.buaa.yyg.baidupager.RecyclerGallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yyg on 2016/5/16.
 */
public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> images;
    private onItemClickListener itemClickListener;
    private String dirAbsolutePath;

    public RecyclerGalleryAdapter(Context context, ArrayList<String> images, String dirAbsolutePath) {
        this.context = context;
        this.images = images;
        this.dirAbsolutePath = dirAbsolutePath;
    }

    /**
     * 创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_gallery, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
        return viewHolder;
    }

    /**
     * 设置值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (TextUtils.isEmpty(dirAbsolutePath)) {
            //文件夹路径为空，加载网络图片
            setImgFromUrl(position, holder.imageView);
        } else {
            setImgFromDir(position, holder.imageView);
        }

        //如果设置了回调，则设置点击事件
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    /**
     * 从网络url获取路径
     * @param position
     */
    private void setImgFromUrl(int position, ImageView imageView) {
        Glide.with(context)
                .load(images.get(position))
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
        Glide.with(context)
                .load(new File(dirAbsolutePath + "/" +  images.get(position)))
                .error(R.mipmap.turn_right)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(1)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
        ImageView imageView;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
