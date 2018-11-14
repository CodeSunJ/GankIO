package com.sunj.gankio.ui.view;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 10:52 AM
 */

public interface IReadCategoryView extends BaseView {

    void getReadMainCategorySuccess(GankBaseResponse<ReadMainCategoryData> response);
    void getReadMainCategoryFailure();

    void getReadSubCategorySuccess(GankBaseResponse<ReadSubCategoryData> response);
    void getReadSubCategoryFailure();
}
