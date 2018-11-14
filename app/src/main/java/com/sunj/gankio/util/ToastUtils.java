package com.sunj.gankio.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/13 8:31 PM
 */

public class ToastUtils {

    public static void show(Context context, String text) {
        Toast mToast = null;
        if (mToast == null) {
            mToast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
            mToast.setText(text);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
