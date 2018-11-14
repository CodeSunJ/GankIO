package com.sunj.gankio.ui.view;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadArticleData;
import com.sunj.gankio.ui.base.BaseView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/14 5:19 PM
 */

public interface IReadArticleView extends BaseView {

    void getReadArticlesSuccess(GankBaseResponse<ReadArticleData> response);
    void getReadArticlesFailure();

    void loadMoreReadArticlesSuccess(GankBaseResponse<ReadArticleData> response);
    void loadMoreReadArticlesFailure();
}
