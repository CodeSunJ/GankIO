package com.sunj.gankio.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunj.gankio.R;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 1:59 PM
 */

public abstract class BaseMultiPagesFragment extends Fragment{

    public static final String ARGUMENT_CHILD_FRAGMENT_TITLES = "ARGUMENT_CHILD_FRAGMENT_TITLES";

    protected ArrayList<String> mTitleList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleList = getArguments().getStringArrayList(ARGUMENT_CHILD_FRAGMENT_TITLES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi_pages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    protected void initView(View view, Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tablayout);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getChildFragment(position);
            }

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        });
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected abstract Fragment getChildFragment(int position);

}
