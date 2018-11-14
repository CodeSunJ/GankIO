package com.sunj.gankio.ui.view;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/27 10:54 AM
 */

public interface ICategoryView extends BaseView {

    void refreshSuccess(GankBaseResponse<GankCategoryData> response);
    void refreshFailure();

    void loadMoreSuccess(GankBaseResponse<GankCategoryData> response);
    void loadMoreFailure();
}
