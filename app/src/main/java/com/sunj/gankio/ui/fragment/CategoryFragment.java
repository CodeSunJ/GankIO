package com.sunj.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.ui.base.BaseListFragment;
import com.sunj.gankio.ui.presenter.CategoryPresenter;
import com.sunj.gankio.ui.view.ICategoryView;
import com.sunj.gankio.util.ToastUtils;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 3:39 PM
 */

public abstract class CategoryFragment extends BaseListFragment<GankCategoryData, CategoryPresenter>
        implements ICategoryView {

    protected static final String ARGUMENT_CATEGORY_TITLE = "ARGUMENT_CATEGORY_TITLE";
    protected static final int CONSTANT_REQUEST_COUNT = 15;
    protected int mCurPage = 1;
    protected String mCategory = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = getArguments().getString(ARGUMENT_CATEGORY_TITLE);
    }

    @Override
    protected CategoryPresenter getPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected void onLoadData() {
        mRefreshRecyclerView.enablePullToRefresh();
        mRefreshRecyclerView.enableLoadMore();
        mRefreshRecyclerView.pullToRefresh();
        mPresenter.refreshData(mCategory, CONSTANT_REQUEST_COUNT, 0);
    }

    @Override
    public void refreshSuccess(GankBaseResponse<GankCategoryData> response) {
        List<GankCategoryData> rList = response.getResults();
        if (!mIsDataInited) {
            mIsDataInited = true;
            mDataList.clear();
            mDataList.addAll(rList);
        } else {
            if (rList != null && rList.size() > 0 && mDataList.size() > 0) {
                String descFrResponse = rList.get(0).get_id();
                String descFrData = mDataList.get(0).get_id();
                if (!TextUtils.isEmpty(descFrResponse) && !TextUtils.isEmpty(descFrData) &&
                        descFrData.equals(descFrResponse)) {
                    mRefreshRecyclerView.completeRefresh();
                    ToastUtils.show(this.getContext(), getString(R.string.up_to_date));
                    return;
                } else {
                    mDataList.clear();
                    mDataList.addAll(rList);
                }
            }
        }
        mAdapter.notifyItemInserted(mDataList.size()-1);
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this.getContext(), getString(R.string.refresh_success));
    }

    @Override
    public void refreshFailure() {
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this.getContext(), getString(R.string.refresh_failure));
    }

    @Override
    public void loadMoreSuccess(GankBaseResponse<GankCategoryData> response) {
        mDataList.addAll(response.getResults());
        mAdapter.notifyItemInserted(mDataList.size()-1);
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this.getContext(), getString(R.string.load_more_success));
    }

    @Override
    public void loadMoreFailure() {
        mCurPage--;
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this.getContext(), getString(R.string.load_more_failure));
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        return new BaseRecyclerViewAdapter(this.getContext().getApplicationContext()) {
            @Override
            protected int setItemViewType(int position) {
                return CategoryFragment.this.getItemViewType(position);
            }

            @Override
            protected void bindByViewHolder(BaseViewHolder holder, int itemViewType, int position) {
                CategoryFragment.this.bindViewHolder(holder, itemViewType, position);
            }

            @Override
            protected int getItemViewLayoutResID(int viewType) {
                return CategoryFragment.this.getItemViewLayoutResID(viewType);
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }
        };
    }

    @Override
    public void onPullToRefresh() {
        mPresenter.refreshData(mCategory, CONSTANT_REQUEST_COUNT, 0);
    }

    @Override
    public void onLoadMore() {
        mCurPage++;
        mPresenter.loadMoreData(mCategory, CONSTANT_REQUEST_COUNT, mCurPage);
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return null;
    }

    protected abstract void bindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int itemViewType, int position);

    protected abstract int getItemViewType(int position);

    protected abstract int getItemViewLayoutResID(int viewType);
}
