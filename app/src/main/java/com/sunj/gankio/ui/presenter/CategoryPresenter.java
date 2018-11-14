package com.sunj.gankio.ui.presenter;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.net.GankCallback;
import com.sunj.gankio.net.GankIOServiceManager;
import com.sunj.gankio.ui.base.BasePresenter;
import com.sunj.gankio.ui.view.ICategoryView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/27 10:55 AM
 */

public class CategoryPresenter extends BasePresenter<ICategoryView> {

    public void refreshData(String category, int count, int page) {
        GankIOServiceManager.getInstance()
                .getGankCategoryData(category, count, page, new GankCallback<GankBaseResponse<GankCategoryData>>() {
            @Override
            public void onSuccess(GankBaseResponse<GankCategoryData> response) {
                if (isViewAttached()) {
                    getView().refreshSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().refreshFailure();
                }
            }
        });
    }

    public void loadMoreData(String category, int count, int page) {
        GankIOServiceManager.getInstance()
                .getGankCategoryData(category, count, page, new GankCallback<GankBaseResponse<GankCategoryData>>() {
            @Override
            public void onSuccess(GankBaseResponse<GankCategoryData> response) {
                if (isViewAttached()) {
                    getView().loadMoreSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().loadMoreFailure();
                }
            }
        });
    }
}
