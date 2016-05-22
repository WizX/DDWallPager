package com.buaa.yyg.ddpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.yyg.ddpager.R;
import com.buaa.yyg.ddpager.domain.HomeGrid;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by yyg on 2016/4/25.
 */
public class GridViewAdapter extends BaseAdapter{

    private Context context;
    private List<HomeGrid> gridData;

    /**
     * 构造方法
     */
    public GridViewAdapter(Context context, List<HomeGrid> gridData) {
        this.context = context;
        this.gridData = gridData;
    }

    @Override
    public int getCount() {
        return gridData.size();
    }

    @Override
    public Object getItem(int position) {
        return gridData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //第一次加载
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_name);

            //动态设置高度，对应与DisGridView的onMeasure，解决布局冲突
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 940));

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(gridData.get(position).getType());
        Glide.with(context)
                .load(gridData.get(position).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(viewHolder.img);

        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView tv;
    }
}
