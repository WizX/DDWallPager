package com.buaa.yyg.baidupager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.adapter.GridViewAdapter;
import com.buaa.yyg.baidupager.domain.HomeGrid;
import com.buaa.yyg.baidupager.view.DisGridView;
import com.buaa.yyg.baidupager.view.DisScrollView;
import com.buaa.yyg.baidupager.view.VPScrollLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by yyg on 2016/4/22.
 */
public class HomeFragment extends Fragment {

    private ViewPager myViewPager;
    private View view;
    private VPScrollLayout vpScroll;
    private List<View> bitmap = new ArrayList<>();
    private List<HomeGrid> gridData = new ArrayList<>();
    private DisGridView mGridView;
    private DisScrollView disScroolView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 绑定对象，拿到ViewPager对象
     * @param view
     */
    private void findView(View view) {
        vpScroll = (VPScrollLayout) view.findViewById(R.id.vp_scroll);
        //拿到VPScrollLayout的ViewPager对象
        myViewPager = vpScroll.getViewpager();

        //找到GridView
        mGridView = (DisGridView) view.findViewById(R.id.gridview);
        disScroolView = (DisScrollView) view.findViewById(R.id.disScroolView);
    }

    /**
     * 初始化
     */
    private void init() {
        initVPData();
        initGridData();
        //设置ViewPager的adap
        myViewPager.setAdapter(new MyAdapter());
        //设置多长时间轮播
        vpScroll.setPagerFromTime(1000);
        //设置GridView的adapter
        mGridView.setAdapter(new GridViewAdapter(getActivity(), gridData));
        //设置每次进入现最前面
//        disScroolView.smoothScrollTo(0,0);
    }

    /**
     * 初始化GridView的数据
     */
    private void initGridData() {
        for (int i = 0; i < 10; i++) {
            HomeGrid grid = new HomeGrid();
            grid.setImg(R.mipmap.nice);
            grid.setType("美女");
            gridData.add(grid);
        }
    }

    /**
     * 初始化图片
     */
    private void initVPData() {
        setVPRes(R.mipmap.img1);
        setVPRes(R.mipmap.img2);
        setVPRes(R.mipmap.img3);
        setVPRes(R.mipmap.img4);
    }

    private void setVPRes(int resId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.vp_scroll_item, null);
        view.findViewById(R.id.vpImg).setBackgroundResource(resId);
        bitmap.add(view);
    }

    /**
     * adapter
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bitmap.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除
            container.removeView(bitmap.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(bitmap.get(position));
            return bitmap.get(position);
        }
    }

}
