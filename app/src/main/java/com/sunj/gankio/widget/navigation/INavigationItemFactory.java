package com.sunj.gankio.widget.navigation;

import android.content.Context;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 11:32 AM
 */

public interface INavigationItemFactory {
    AbNavigationItemView createNavigationItemView(Context context, int index);
}
