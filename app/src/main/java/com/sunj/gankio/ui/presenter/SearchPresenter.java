package com.sunj.gankio.ui.presenter;

import com.sunj.gankio.entity.GankSearchResponse;
import com.sunj.gankio.entity.SearchData;
import com.sunj.gankio.net.GankCallback;
import com.sunj.gankio.net.GankIOServiceManager;
import com.sunj.gankio.ui.base.BasePresenter;
import com.sunj.gankio.ui.view.ISearchView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 10:54 AM
 */

public class SearchPresenter extends BasePresenter<ISearchView> {

    public void getSearchData(String query, String category, int count, int page) {
        GankIOServiceManager.getInstance()
                .getSearchData(query, category, count, page, new GankCallback<GankSearchResponse<SearchData>>() {
            @Override
            public void onSuccess(GankSearchResponse<SearchData> response) {
                if (isViewAttached()) {
                    getView().getSearchDataSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().getSearchDataFailure();
                }
            }
        });
    }

    public void loadMoreSearchData(String query, String category, int count, int page) {
        GankIOServiceManager.getInstance()
                .getSearchData(query, category, count, page, new GankCallback<GankSearchResponse<SearchData>>() {
            @Override
            public void onSuccess(GankSearchResponse<SearchData> response) {
                if (isViewAttached()) {
                    getView().loadMoreSearchDataSuccess(response);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().loadMoreSearchDataFailure();
                }
            }
        });
    }

}
