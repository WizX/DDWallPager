package com.buaa.yyg.baidupager.galleryrecycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyg on 2016/5/13.
 */
public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<String> image = new ArrayList<>();
    private onItemClickListener listener;

    public GalleryRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(context)
                .load(image.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .crossFade()
                .into(holder.img);

        //设置回调监听
        if (listener != null) { //如果设置了监听那么它就不为空，然后回调相应的方法
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition(); //得到当前点击item的位置pos
                    listener.ItemClickListener(holder.img, pos); //把事件交给我们实现的接口那里处理
                }
            });
            holder.img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.ItemLongClickListener(holder.img, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (image != null) {
            return image.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
        }
    }

    /**
     * 在原有数据后面添加更多数据
     * @param imageUrl
     */
    public void addItem(List<String> imageUrl, int position) {
        image.addAll(imageUrl);
        notifyDataSetChanged();
    }

    /**
     * 移除集合相应位置的元素，用于长按删除
     * @param position
     */
    public void removeItem(int position) {
        image.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 自定义条目点击侦听，用于单击和长按侦听
     */
    public interface onItemClickListener {
        void ItemClickListener(View view, int position);
        void ItemLongClickListener(View view, int position);
    }

    public void setOnClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
