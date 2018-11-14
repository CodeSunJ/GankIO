package com.sunj.gankio.ui.presenter;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadArticleData;
import com.sunj.gankio.net.GankCallback;
import com.sunj.gankio.net.GankIOServiceManager;
import com.sunj.gankio.ui.base.BasePresenter;
import com.sunj.gankio.ui.view.IReadArticleView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/11/14 11:19 AM
 */

public class ReadArticlePresenter extends BasePresenter<IReadArticleView> {

    public void getReadArticleData(String id, int count, final int page) {
        GankIOServiceManager.getInstance()
                .getReadArticleData(id, count, page, new GankCallback<GankBaseResponse<ReadArticleData>>() {
            @Override
            public void onSuccess(GankBaseResponse<ReadArticleData> response) {
                if (isViewAttached()) {
                    getView().getReadArticlesSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().getReadArticlesFailure();
                }
            }
        });
    }

    public void loadMoreReadArticleData(String id, int count, final int page) {
        GankIOServiceManager.getInstance()
                .getReadArticleData(id, count, page, new GankCallback<GankBaseResponse<ReadArticleData>>() {
            @Override
            public void onSuccess(GankBaseResponse<ReadArticleData> response) {
                if (isViewAttached()) {
                    getView().loadMoreReadArticlesSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().loadMoreReadArticlesFailure();
                }
            }
        });
    }
    
}
