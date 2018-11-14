package com.sunj.gankio.ui.presenter;

import com.sunj.gankio.entity.GankHomeResponse;
import com.sunj.gankio.net.GankCallback;
import com.sunj.gankio.net.GankIOServiceManager;
import com.sunj.gankio.ui.base.BasePresenter;
import com.sunj.gankio.ui.view.IHomeView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 10:59 AM
 */

public class HomePresenter extends BasePresenter<IHomeView> {

    public void getHomeGankData() {
        GankIOServiceManager.getInstance()
                .getHomeGankData(new GankCallback<GankHomeResponse>() {
            @Override
            public void onSuccess(GankHomeResponse response) {
                if (isViewAttached()) {
                    getView().getHomeDataSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().getHomeDataFailure();
                }
            }
        });
    }
}
