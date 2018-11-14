package com.sunj.gankio.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/13 9:25 PM
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private Boolean mIsViewCreated = false;
    protected Boolean mIsDataInited = false;
    private Boolean mIsVisible = false;

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(), container, false);
        init(view);
        mIsViewCreated = true;
        onLazyLoad();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        onLazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    protected void goToActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onLazyLoad() {
        if (mIsViewCreated && mIsVisible && !mIsDataInited) {
            onLoadData();
        }
    }

    protected void onLoadData() {

    }

    protected abstract P getPresenter();

    protected abstract void init(View view);

    protected abstract int getLayoutResID();
}
