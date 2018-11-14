package com.sunj.gankio.ui.base;

import android.support.v7.widget.RecyclerView;

import com.sunj.gankio.R;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.widget.refresh.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 3:40 PM
 */

public abstract class BaseListActivity<L, P extends BasePresenter> extends BaseActivity<P>
        implements RefreshRecyclerView.OnListItemRefreshListener {

    protected BaseRecyclerViewAdapter mAdapter;
    protected RefreshRecyclerView mRefreshRecyclerView;
    protected List<L> mDataList = new ArrayList<>();

    @Override
    protected void init() {
        mAdapter = getRecyclerViewAdapter();
        mRefreshRecyclerView = (RefreshRecyclerView) findViewById(R.id.refresh_view);
        mRefreshRecyclerView.setLayoutManager(getListLayoutManager());
        mRefreshRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.setRefreshListener(this);
        mRefreshRecyclerView.addItemDecoration(null);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_list;
    }

    protected void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPullToRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    protected abstract RecyclerView.LayoutManager getListLayoutManager();

    protected abstract BaseRecyclerViewAdapter getRecyclerViewAdapter();

}
