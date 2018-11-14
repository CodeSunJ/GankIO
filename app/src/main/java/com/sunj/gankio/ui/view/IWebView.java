package com.sunj.gankio.ui.view;

import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/14 3:36 PM
 */

public interface IWebView extends BaseView {

    void showWebViewProgress(int progress);
    void setWebViewTitle(String title);
}
