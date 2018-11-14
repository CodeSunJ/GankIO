package com.sunj.gankio.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.ui.base.BaseListActivity;
import com.sunj.gankio.ui.presenter.ReadCategoryPresenter;
import com.sunj.gankio.ui.view.IReadCategoryView;
import com.sunj.gankio.util.ToastUtils;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/13 10:11 PM
 */

public class ReadCategoryActivity extends BaseListActivity<ReadSubCategoryData, ReadCategoryPresenter>
        implements IReadCategoryView {

    public static final String BUNDLE_READ_SUB_CATEGORY = "BUNDLE_READ_SUB_CATEGORY";
    public static final String BUNDLE_READ_SUB_CATEGORY_TITLE = "BUNDLE_READ_SUB_CATEGORY_TITLE";
    private String mCategory = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = getIntent().getStringExtra(BUNDLE_READ_SUB_CATEGORY);
        if (!TextUtils.isEmpty(mCategory)) {
            mPresenter.getReadSubCategoryData(mCategory);
        }
    }

    @Override
    protected void init() {
        super.init();
        initToolbar(TOOLBAR_MODE_BACK, getIntent().getStringExtra(BUNDLE_READ_SUB_CATEGORY_TITLE));
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return new GridLayoutManager(getApplicationContext(), 3);
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
                ImageLoader.loadImage(ReadCategoryActivity.this, mDataList.get(position).getIcon(),
                        (ImageView) holder.findViewById(R.id.iv_icon));
                holder.setOnClickListener(R.id.ll_container, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ReadArticleActivity.BUNDLE_READ_SUB_CATEGORY_ID,
                                mDataList.get(position).getId());
                        bundle.putString(ReadArticleActivity.BUNDLE_READ_SUB_CATEGORY_TITLE,
                                mDataList.get(position).getTitle());
                        goToActivity(ReadArticleActivity.class, bundle);
                    }
                });
            }

            @Override
            protected int getItemViewLayoutResID(int viewType) {
                return R.layout.item_read_sub_category;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }
        };
    }

    @Override
    protected ReadCategoryPresenter getPresenter() {
        return new ReadCategoryPresenter();
    }

    @Override
    public void getReadMainCategorySuccess(GankBaseResponse<ReadMainCategoryData> response) {
    }

    @Override
    public void getReadMainCategoryFailure() {

    }

    @Override
    public void getReadSubCategorySuccess(GankBaseResponse<ReadSubCategoryData> response) {
        mDataList = response.getResults();
        notifyDataChanged();
    }

    @Override
    public void getReadSubCategoryFailure() {
        ToastUtils.show(this, getString(R.string.load_more_failure));
    }
}
