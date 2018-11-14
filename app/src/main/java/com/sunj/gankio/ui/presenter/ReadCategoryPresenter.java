package com.sunj.gankio.ui.presenter;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.net.GankCallback;
import com.sunj.gankio.net.GankIOServiceManager;
import com.sunj.gankio.ui.base.BasePresenter;
import com.sunj.gankio.ui.view.IReadCategoryView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/18 3:39 PM
 */

public class ReadCategoryPresenter extends BasePresenter<IReadCategoryView> {

    public void getReadMainCategoryData() {
        GankIOServiceManager.getInstance()
                .getReadMainCategoryData(new GankCallback<GankBaseResponse<ReadMainCategoryData>>() {
            @Override
            public void onSuccess(GankBaseResponse<ReadMainCategoryData> response) {
                if (isViewAttached()) {
                    getView().getReadMainCategorySuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().getReadMainCategoryFailure();
                }
            }
        });
    }

    public void getReadSubCategoryData(String category) {
        GankIOServiceManager.getInstance()
                .getReadSubCategoryData(category, new GankCallback<GankBaseResponse<ReadSubCategoryData>>() {
            @Override
            public void onSuccess(GankBaseResponse<ReadSubCategoryData> response) {
                if (isViewAttached()) {
                    getView().getReadSubCategorySuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().getReadSubCategoryFailure();
                }
            }
        });
    }

}
