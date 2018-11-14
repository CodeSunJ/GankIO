package com.sunj.gankio.widget.navigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sunj.gankio.widget.navigation.INavigationItemFactory;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/19 7:39 PM
 */

public class NavigationBar extends LinearLayout implements View.OnClickListener {

    private int mShowIndex = -1;

    private OnNavigationItemClickListener mListener;

    public NavigationBar(Context context) {
        super(context);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onClick(View v) {
        int selectedIndex = ((AbNavigationItemView) v).getIndex();
        show(selectedIndex);
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    public void addAllItems(List<? extends INavigationItemFactory> iNavigationItemFactories) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        for (int i=0; i<iNavigationItemFactories.size(); i++) {
            AbNavigationItemView view = iNavigationItemFactories.get(i).createNavigationItemView(getContext(), i);
            addView(view, layoutParams);
            view.setOnClickListener(this);
            view.hide();
        }
        show(0);
    }

    public void show(int index) {
        if (mShowIndex != index) {
            if (mShowIndex >= 0) {
                ((AbNavigationItemView) getChildAt(mShowIndex)).hide();
            }
            if (index >= 0) {
                ((AbNavigationItemView) getChildAt(index)).show();
            }
            if (mListener != null) {
                mListener.onNavigationItemClick(mShowIndex, index);
            }
            mShowIndex = index;
        }
    }

    public void setOnNavigationItemClickListener(OnNavigationItemClickListener listener) {
        mListener = listener;
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                show(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public interface OnNavigationItemClickListener {
        void onNavigationItemClick(int preIndex, int index);
    }
}
