package com.buaa.yyg.baidupager.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.Contentlist;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by yyg on 2016/5/11.
 */
public class MySearchImageAdapter extends RecyclerView.Adapter<MySearchImageAdapter.Holder>{
    private List<Contentlist> image;
    private Context context;
    private onItemClickListener listener;

    public MySearchImageAdapter(Context context, List<Contentlist> image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_image, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        Glide.with(context)
                .load(image.get(position).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.imageView);

        if (listener != null) { //如果设置了监听那么它就不为空，然后回调相应的方法
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition(); //得到当前点击item的位置pos
                    listener.ItemClickListener(holder.imageView, pos); //把事件交给我们实现的接口那里处理
                }
            });
            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.ItemLongClickListener(holder.imageView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_search_image_item);
        }

    }

    public interface onItemClickListener {
        void ItemClickListener(View view, int position);
        void ItemLongClickListener(View view, int position);
    }

    public void setOnClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
