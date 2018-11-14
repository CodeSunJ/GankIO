package com.sunj.gankio.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadArticleData;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.ui.base.BaseListActivity;
import com.sunj.gankio.ui.presenter.ReadArticlePresenter;
import com.sunj.gankio.ui.view.IReadArticleView;
import com.sunj.gankio.util.ToastUtils;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/14 11:05 AM
 */

public class ReadArticleActivity extends BaseListActivity<ReadArticleData, ReadArticlePresenter>
        implements IReadArticleView {

    public static final String BUNDLE_READ_SUB_CATEGORY_ID = "BUNDLE_READ_SUB_CATEGORY_ID";
    public static final String BUNDLE_READ_SUB_CATEGORY_TITLE = "BUNDLE_READ_SUB_CATEGORY_TITLE";
    private String mId = "";
    protected static final int CONSTANT_REQUEST_COUNT = 15;
    protected int mCurPage = 1;

    @Override
    protected void init() {
        super.init();
        mId = getIntent().getStringExtra(BUNDLE_READ_SUB_CATEGORY_ID);
        initToolbar(TOOLBAR_MODE_BACK, getIntent().getStringExtra(BUNDLE_READ_SUB_CATEGORY_TITLE));
        if (!TextUtils.isEmpty(mId)) {
            mRefreshRecyclerView.enablePullToRefresh();
            mRefreshRecyclerView.pullToRefresh();
            mPresenter.getReadArticleData(mId, CONSTANT_REQUEST_COUNT, mCurPage);
        }
        mRefreshRecyclerView.enableLoadMore();
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        return new BaseRecyclerViewAdapter(getApplicationContext()) {
            @Override
            protected int setItemViewType(int position) {
                return 0;
            }

            @Override
            protected void bindByViewHolder(BaseViewHolder holder, int itemViewType, final int position) {
                holder.setText(R.id.tv_title, mDataList.get(position).getTitle());
                holder.setText(R.id.tv_desc, mDataList.get(position).getContent());
                ImageLoader.loadImage(ReadArticleActivity.this, mDataList.get(position).getCover(),
                        (ImageView) holder.findViewById(R.id.iv_cover));
                holder.setOnClickListener(R.id.ll_container, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewActivity.BUNDLE_URL_TITLE, mDataList.get(position).getTitle());
                        bundle.putString(WebViewActivity.BUNDLE_CONTENT_URL, mDataList.get(position).getUrl());
                        goToActivity(WebViewActivity.class, bundle);
                    }
                });
            }

            @Override
            protected int getItemViewLayoutResID(int viewType) {
                return R.layout.item_read_article;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }
        };
    }

    @Override
    protected ReadArticlePresenter getPresenter() {
        return new ReadArticlePresenter();
    }

    @Override
    public void onPullToRefresh() {
        mPresenter.getReadArticleData(mId, CONSTANT_REQUEST_COUNT, 0);
    }

    @Override
    public void onLoadMore() {
        mCurPage ++;
        mPresenter.loadMoreReadArticleData(mId, CONSTANT_REQUEST_COUNT, mCurPage);
    }

    @Override
    public void getReadArticlesSuccess(GankBaseResponse<ReadArticleData> response) {
        List<ReadArticleData> dataList = response.getResults();
        if (mDataList == null || mDataList.size() == 0) {
            mDataList.clear();
            mDataList.addAll(dataList);
        } else {
            if (mDataList.get(0).get_id().equals(dataList.get(0).get_id())) {
                mRefreshRecyclerView.completeRefresh();
                ToastUtils.show(this, getString(R.string.up_to_date));
                return;
            } else {
                mDataList.clear();
                mDataList.addAll(dataList);
            }
        }
        mRefreshRecyclerView.completeRefresh();
        mAdapter.notifyItemInserted(mDataList.size() - 1);
    }

    @Override
    public void getReadArticlesFailure() {
        ToastUtils.show(this, getString(R.string.refresh_failure));
    }

    @Override
    public void loadMoreReadArticlesSuccess(GankBaseResponse<ReadArticleData> response) {
        mDataList.addAll(response.getResults());
        mAdapter.notifyItemInserted(mDataList.size() - 1);
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.load_more_success));
    }

    @Override
    public void loadMoreReadArticlesFailure() {
        mCurPage--;
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.load_more_failure));

    }
}
