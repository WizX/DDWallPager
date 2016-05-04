package com.buaa.yyg.baidupager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.domain.HomeGrid;
import com.loopj.android.image.SmartImageView;

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
            viewHolder.img = (SmartImageView) convertView.findViewById(R.id.mySmartImageView);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_nice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.img.setBackgroundResource(gridData.get(position).getImg());
        if (position == 0) {
            viewHolder.img.setScaleType(ImageView.ScaleType.MATRIX);
            viewHolder.img.setImageResource(R.mipmap.hot);
        }
        viewHolder.tv.setText(gridData.get(position).getType());
        //动态设置高度，对应与DisGridView的onMeasure，解决布局冲突
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 380));
        return convertView;
    }

    private static class ViewHolder {
        private SmartImageView img;
        private TextView tv;
    }
}
