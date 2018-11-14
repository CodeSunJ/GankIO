package com.sunj.gankio.widget.navigation;

import android.content.Context;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 11:47 AM
 */

public class NavigationItem implements INavigationItemFactory {

    private int mShowImageResID;
    private int mHideImageResID;
    private String mText;

    public static NavigationItem create(int showImageResID, int hideImageResID, String text) {
        return new NavigationItem(showImageResID, hideImageResID, text);
    }

    private NavigationItem(int mShowImageResID, int mHideImageResID, String mText) {
        this.mShowImageResID = mShowImageResID;
        this.mHideImageResID = mHideImageResID;
        this.mText = mText;
    }

    @Override
    public AbNavigationItemView createNavigationItemView(Context context, int index) {
        NavigationItemView view = new NavigationItemView(context);
        view.setIndex(index);
        view.setImagesAndText(mShowImageResID, mHideImageResID, mText);
        return view;
    }
}
