package com.sunj.gankio.widget.navigation;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunj.gankio.R;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 11:50 AM
 */

public class NavigationItemView extends AbNavigationItemView{

    private static final String CONSTANT_TEXT_SELECTED_COLOR = "#3F51B5";
    private static final String CONSTANT_TEXT_UNSELECTED_COLOR = "#bfbfbf";

    private int mShowImageResID;
    private int mHideImageResID;

    private ImageView mIconIv;
    private TextView mTitleTv;

    public NavigationItemView(Context context) {
        super(context);
    }

    public NavigationItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.sel_press);
    }

    @Override
    protected void initView() {
        mIconIv = findViewById(R.id.iv_icon);
        mTitleTv = findViewById(R.id.tv_title);
    }

    @Override
    protected void show() {
        mTitleTv.setTextColor(Color.parseColor(CONSTANT_TEXT_SELECTED_COLOR));
        mIconIv.setImageResource(mShowImageResID);
    }

    @Override
    protected void hide() {
        mTitleTv.setTextColor(Color.parseColor(CONSTANT_TEXT_UNSELECTED_COLOR));
        mIconIv.setImageResource(mHideImageResID);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.widget_navigationitem_view;
    }

    public void setImagesAndText(int mShowImageResID, int mHideImageResID, String text) {
        this.mShowImageResID = mShowImageResID;
        this.mHideImageResID = mHideImageResID;
        mTitleTv.setText(text);
        hide();
    }
}
