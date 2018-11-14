package com.sunj.gankio.ui.view;

import com.sunj.gankio.entity.GankHomeResponse;
import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/20 10:55 AM
 */

public interface IHomeView extends BaseView {

    void getHomeDataSuccess(GankHomeResponse response);
    void getHomeDataFailure();
}
