package com.sunj.gankio.widget.popupwindow;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/13 9:18 PM
 */

public class PopupView {

    public static PopupWindow getPopupWindow(Activity activity, int layoutResID) {
        View contentView = LayoutInflater.from(activity).inflate(layoutResID, null);
        PopupWindow popupWindow = new PopupWindow(activity);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        return popupWindow;
    }

}
