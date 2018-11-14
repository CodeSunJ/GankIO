package com.sunj.gankio.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunj.gankio.R;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.widget.refresh.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 2:29 PM
 */

public abstract class BaseListFragment<L, P extends BasePresenter> extends BaseFragment<P>
        implements RefreshRecyclerView.OnListItemRefreshListener {

    protected BaseRecyclerViewAdapter mAdapter;
    protected RefreshRecyclerView mRefreshRecyclerView;
    protected List<L> mDataList = new ArrayList<>();

    @Override
    protected void init(View view) {
        mAdapter = getRecyclerViewAdapter();
        mRefreshRecyclerView = view.findViewById(R.id.refresh_view);
        mRefreshRecyclerView.enableLoadMore();
        mRefreshRecyclerView.enablePullToRefresh();
        mRefreshRecyclerView.setLayoutManager(getListLayoutManager());
        mRefreshRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.setRefreshListener(this);
        mRefreshRecyclerView.addItemDecoration(null);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    protected abstract RecyclerView.LayoutManager getListLayoutManager();

    protected abstract BaseRecyclerViewAdapter getRecyclerViewAdapter();

}
