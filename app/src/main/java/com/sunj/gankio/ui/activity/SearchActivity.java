package com.sunj.gankio.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankSearchResponse;
import com.sunj.gankio.entity.SearchData;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.ui.base.BaseListActivity;
import com.sunj.gankio.ui.presenter.SearchPresenter;
import com.sunj.gankio.ui.view.ISearchView;
import com.sunj.gankio.util.ConstantUtils;
import com.sunj.gankio.util.ToastUtils;
import com.sunj.gankio.widget.option.OptionView;
import com.sunj.gankio.widget.popupwindow.PopupView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/13 10:12 PM
 */

public class SearchActivity extends BaseListActivity<SearchData, SearchPresenter> implements ISearchView {

    private EditText mTxEt;
    private PopupWindow mPopWinow;
    private OptionView mCategoryOptionView;
    private InputMethodManager mInputMethodManager;
    private int mPage = 1;
    private int mCount = 15;
    private String mCategory = ConstantUtils.ALL;
    private List<String> mCategoryList = new ArrayList<>();

    {
        mCategoryList.add(ConstantUtils.ALL);
        mCategoryList.add(ConstantUtils.ANDROID);
        mCategoryList.add(ConstantUtils.IOS);
        mCategoryList.add(ConstantUtils.VIDEO);
        mCategoryList.add(ConstantUtils.IMAGES);
        mCategoryList.add(ConstantUtils.EXPANDING_RES);
        mCategoryList.add(ConstantUtils.WEB_FRONT_END);
        mCategoryList.add(ConstantUtils.RECOMMAND);
        mCategoryList.add(ConstantUtils.APP);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        super.init();
        initToolbar(TOOLBAR_MODE_BACK, "");
        mTxEt = (EditText) findViewById(R.id.et_search);
        mTxEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyBoard();
            }
        });
        mRefreshRecyclerView.enableLoadMore();
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected int getMenuResID() {
        return R.menu.menu_search;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                search();
                break;
            case R.id.menu_search_category:
                showSearchCategoryItems();
                break;
        }
        return super.onMenuItemClick(item);
    }

    private void search() {
        if (!TextUtils.isEmpty(mTxEt.getText().toString())) {
            mPage = 0;
            mPresenter.getSearchData(mTxEt.getText().toString(), mCategory, mCount, mPage);
            hideKeyBoard();
            mRefreshRecyclerView.enablePullToRefresh();
            mRefreshRecyclerView.pullToRefresh();
        } else {
            ToastUtils.show(this, getString(R.string.input_first));
        }
    }

    private void showSearchCategoryItems() {
        if (mPopWinow == null) {
            mPopWinow = PopupView.getPopupWindow(this, R.layout.ppw_category);
            mPopWinow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    SearchActivity.this.setActivityWindowAlpha(1f);
                }
            });
            mCategoryOptionView = mPopWinow.getContentView().findViewById(R.id.option_view_category);
            mCategoryOptionView.setOptionTexts(mCategoryList);
            mCategoryOptionView.setOnOptionViewSelectedListener(new OptionView.OnOptionViewSelectedListener() {
                @Override
                public void onSelectedListener(int index) {
                    mCategory = mCategoryList.get(index);
                    mCategoryOptionView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopWinow.dismiss();
                        }
                    }, 500);
                }
            });
        }
        if (!mPopWinow.isShowing()) {
            mPopWinow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER_HORIZONTAL, 0, 0);
            setActivityWindowAlpha(0.3f);
        } else {
            mPopWinow.dismiss();
        }
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
                holder.setText(R.id.tv_desc, mDataList.get(position).getDesc());
                holder.setText(R.id.tv_who, mDataList.get(position).getWho());
                holder.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewActivity.BUNDLE_URL_TITLE, mDataList.get(position).getDesc());
                        bundle.putString(WebViewActivity.BUNDLE_CONTENT_URL, mDataList.get(position).getUrl());
                        SearchActivity.this.goToActivity(WebViewActivity.class, bundle);
                    }
                });
            }

            @Override
            protected int getItemViewLayoutResID(int viewType) {
                return R.layout.item_category_desc;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }
        };
    }

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void getSearchDataSuccess(GankSearchResponse<SearchData> response) {
        mDataList.clear();
        mDataList.addAll(response.getResults());
        notifyDataChanged();
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.search_success));
        mRefreshRecyclerView.enablePullToRefresh(false);
    }

    @Override
    public void getSearchDataFailure() {
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.search_failure));
        mRefreshRecyclerView.enablePullToRefresh(false);
    }

    @Override
    public void loadMoreSearchDataSuccess(GankSearchResponse<SearchData> response) {
        mDataList.addAll(response.getResults());
        mAdapter.notifyItemInserted(mDataList.size()-1);
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.load_more_success));
    }

    @Override
    public void loadMoreSearchDataFailure() {
        mPage--;
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this, getString(R.string.load_more_failure));
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPage++;
        mPresenter.loadMoreSearchData(mTxEt.getText().toString(), mCategory, mCount, mPage);
    }

    private void showKeyBoard() {
        mTxEt.setFocusable(true);
        mTxEt.setFocusableInTouchMode(true);
        mTxEt.requestFocus();
        mTxEt.findFocus();
        mInputMethodManager.showSoftInput(mTxEt, InputMethodManager.SHOW_FORCED);
    }

    private void hideKeyBoard() {
        mTxEt.setFocusable(false);
        if (mInputMethodManager.isActive()) {
            mInputMethodManager.hideSoftInputFromWindow(mTxEt.getWindowToken(), 0);
        }
    }
}
