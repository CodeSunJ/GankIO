package com.sunj.gankio.ui.base;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/13 9:01 PM
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mView;

    public void attach(T view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }

    protected boolean isViewAttached() {
        return mView != null;
    }

    protected T getView() {
        return mView;
    }

}
