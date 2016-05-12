package com.buaa.yyg.baidupager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.buaa.yyg.baidupager.R;


/**
 * 本地
 * Created by yyg on 2016/4/22.
 */
public class LocalFragment extends Fragment {


    private Button btn_tochosen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_fragment, container, false);
        btn_tochosen = (Button) view.findViewById(R.id.btn_tochosen);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_tochosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到精选
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.myViewPager);
                viewPager.setCurrentItem(1);
            }
        });

    }
}
