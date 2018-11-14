package com.sunj.gankio.widget.navigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 11:41 AM
 */

public abstract class AbNavigationItemView extends LinearLayout {

    private int mIndex;

    public AbNavigationItemView(Context context) {
        super(context);
        init();
    }

    public AbNavigationItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbNavigationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    protected void init() {
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(getLayoutResID(), this, true);
        initView();
    }

    protected abstract void initView();

    protected abstract void show();

    protected abstract void hide();

    protected abstract int getLayoutResID();
}
