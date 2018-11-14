package com.sunj.gankio.ui.view;

import com.sunj.gankio.entity.GankSearchResponse;
import com.sunj.gankio.entity.SearchData;
import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/20 11:12 AM
 */

public interface ISearchView extends BaseView {

    void getSearchDataSuccess(GankSearchResponse<SearchData> response);
    void getSearchDataFailure();

    void loadMoreSearchDataSuccess(GankSearchResponse<SearchData> response);
    void loadMoreSearchDataFailure();
}
