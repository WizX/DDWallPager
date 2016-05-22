package com.buaa.yyg.ddpager.RecyclerGallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yyg on 2016/5/16.
 */
public class MyRecyclerGalleryView extends RecyclerView {

    /**
     * 记录当前第一个view
     */
    private View currentView;
    private OnItemScrollChangeListener itemScrollChangeListener;

    public MyRecyclerGalleryView(Context context) {
        super(context);
        this.setOnScrollListener(new MyOnScrollListener());
    }

    public MyRecyclerGalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(new MyOnScrollListener());
    }

    public MyRecyclerGalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(new MyOnScrollListener());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        currentView = getChildAt(0);
        if (itemScrollChangeListener != null) {
            itemScrollChangeListener.onChange(currentView, getChildPosition(currentView));
        }
    }

    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener itemScrollChangeListener) {
        this.itemScrollChangeListener = itemScrollChangeListener;
    }

    class MyOnScrollListener extends OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        /**
         * 滚动时，判断当前第一个view是否发生变化，发生才回调
         * @param recyclerView
         * @param dx
         * @param dy
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            View newView = getChildAt(0);
            if (itemScrollChangeListener != null) {
                if (newView != null && newView != currentView) {
                    currentView = newView;
                    itemScrollChangeListener.onChange(currentView, getChildPosition(currentView));
                }
            }
        }
    }
}
