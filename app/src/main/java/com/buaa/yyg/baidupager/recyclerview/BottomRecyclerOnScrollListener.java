package com.buaa.yyg.baidupager.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by yyg on 2016/5/13.
 */
public abstract class BottomRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public int lastVisibleItemPosition, visibleItemCount, totalItemCount;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public BottomRecyclerOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        visibleItemCount = staggeredGridLayoutManager.getChildCount();  //界面上可见数量
        totalItemCount = staggeredGridLayoutManager.getItemCount();
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1) {
            onBottomLoadMore(lastVisibleItemPosition);
        }

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
        lastVisibleItemPosition = findmMax(lastVisibleItemPositions);
    }

    private int findmMax(int[] lastVisibleItemPositions) {
        int max = lastVisibleItemPositions[0];
        for (int value : lastVisibleItemPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public abstract void onBottomLoadMore(int lastVisibleItemPosition);
}
