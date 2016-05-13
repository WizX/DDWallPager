package com.buaa.yyg.baidupager.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by yyg on 2016/5/13.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private StaggeredGridLayoutManager.LayoutParams lp;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        lp = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();

        if (position == 0 || position==1){
            outRect.top = space * 2;
        } else {
            outRect.top = space;
        }

        if (lp.getSpanIndex() == 0) {
            outRect.left = space * 2;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = space * 2;
        }
        outRect.bottom = space;

            /*    //注掉的方法总有漏网之鱼，不知为啥
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (position == 0 || position == 1) {
            //用于设置第一行跟顶部的距离
            outRect.top = space;
        }

        if (lp.getSpanIndex() == 0) {
            //用于设同行两个间隔间跟其距离左右屏幕间隔相同
            outRect.right = 0;
        }*/
    }
}

