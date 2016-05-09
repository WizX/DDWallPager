package com.buaa.yyg.baidupager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.buaa.yyg.baidupager.R;
import com.buaa.yyg.baidupager.adapter.GridViewAdapter;
import com.buaa.yyg.baidupager.domain.HomeGrid;
import com.buaa.yyg.baidupager.global.Constant;
import com.buaa.yyg.baidupager.view.DisGridView;
import com.buaa.yyg.baidupager.view.DisScrollView;
import com.buaa.yyg.baidupager.view.VPScrollLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
    private List<String> images = new ArrayList<>();
    private List<HomeGrid> gridData = new ArrayList<>();
    private DisGridView mGridView;
    private DisScrollView disScroolView;
    private RelativeLayout rl_top;

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
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
    }

    /**
     * 初始化
     */
    private void init() {

        for (int i = 1; i <= 4; i++) {
            String vpurl = Constant.URL + "/viewpager/" + i + ".jpg";
            images.add(vpurl);
        }

        if (gridData.size() == 0) {
            initGridData();
        }
        //设置ViewPager的adapter
        myViewPager.setAdapter(new MyAdapter());

        //设置多长时间轮播
        vpScroll.setPagerFromTime(1000);
        //设置GridView的adapter
        mGridView.setAdapter(new GridViewAdapter(getActivity(), gridData));
        //设置每次进入现最前面
//        disScroolView.smoothScrollTo(0,0);

        //解决scrollview自动滚动到底部的问题
        //在初始化的时候就让该界面的顶部的某一个控件获得焦点，滚动条自然就显示到顶部了
        rl_top.setFocusable(true);
        rl_top.setFocusableInTouchMode(true);
        rl_top.requestFocus();
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
     * adapter
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View vpView = getActivity().getLayoutInflater().inflate(R.layout.vp_scroll_item, null);
            container.addView(vpView);
            ImageView imageView = (ImageView) vpView.findViewById(R.id.vpImg);

            Glide.with(HomeFragment.this)
                    .load(images.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(1)
                    .into(imageView);
            return vpView;
        }
    }

}
