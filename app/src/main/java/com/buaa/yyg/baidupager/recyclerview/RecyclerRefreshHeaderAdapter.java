package com.buaa.yyg.baidupager.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.Value;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyg on 2016/5/13.
 */
public class RecyclerRefreshHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Value> image = new ArrayList<>();
    private Context context;
    private onItemClickListener listener;

    public RecyclerRefreshHeaderAdapter(Context context) {
        this.context = context;
    }

    /**
     * 加载布局
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_image, viewGroup, false);
        //可以进行属性设置，甚至事件绑定监听
        return new ItemViewHolder(view);
    }

    /**
     * 数据绑定显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Glide.with(context)
                .load(image.get(position).getContentUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .crossFade()
                .into(itemViewHolder.imageView);

        if (listener != null) { //如果设置了监听那么它就不为空，然后回调相应的方法
            itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition(); //获取当前item的位置
                    listener.ItemClickListener(itemViewHolder.imageView, pos); //把事件交给我们实现的接口那里处理
                }
            });
            itemViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();  //获取当前item的位置
                    listener.ItemLongClickListener(itemViewHolder.imageView, pos);
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

    /**
     * 添加Header
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    /**
     * 自定义viewHolder，持有每个item的所有界面元素
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_search_image_item);
        }
    }

    public ArrayList<String> getAllImage(){
        ArrayList<String> imageString = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            imageString.add(image.get(i).getContentUrl());
        }
        return imageString;
    }

    /**
     * 在原有数据后面添加更多数据
     * @param imageUrl
     */
    public void addMoreItem(List<Value> imageUrl, int lastVisibleItemPosition) {
        image.addAll(imageUrl);


//                imageUrl.addAll(image);
//                image.removeAll(image);
//                image.addAll(imageUrl);

//        notifyDataSetChanged();
       //只刷新插入部分，这样不会造成闪烁
        notifyItemInserted(lastVisibleItemPosition + 1);
    }

    /**
     * 添加数据到原有集合顶部，用于下拉刷新
     * @param imageUrl
     */
    public void addTopItem(List<Value> imageUrl) {
        image.addAll(0, imageUrl);
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
