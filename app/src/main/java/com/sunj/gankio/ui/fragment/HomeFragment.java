package com.sunj.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.entity.GankHomeResponse;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.activity.WebViewActivity;
import com.sunj.gankio.ui.activity.PhotoActivity;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.ui.base.BaseListFragment;
import com.sunj.gankio.ui.presenter.HomePresenter;
import com.sunj.gankio.ui.view.IHomeView;
import com.sunj.gankio.util.ConstantUtils;
import com.sunj.gankio.util.ToastUtils;

import java.util.List;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/13 10:28 PM
 */

public class HomeFragment extends BaseListFragment<GankCategoryData, HomePresenter> implements IHomeView {

    @Override
    protected void init(View view) {
        super.init(view);
        mRefreshRecyclerView.enablePullToRefresh();
        mRefreshRecyclerView.pullToRefresh();
        mPresenter.getHomeGankData();
    }

    @Override
    public void getHomeDataSuccess(GankHomeResponse response) {
        mDataList.clear();
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getAndroid());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getApp());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getiOS());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getVideo());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getExpandingRes());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getRecommend());
        mDataList.add(new GankCategoryData());
        mDataList.addAll(response.getResults().getImage());
        notifyDataChanged();
        mRefreshRecyclerView.completeRefresh();
        mRefreshRecyclerView.enablePullToRefresh(false);
    }

    @Override
    public void getHomeDataFailure() {
        mRefreshRecyclerView.completeRefresh();
        ToastUtils.show(this.getContext(), "刷新失败！");
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),
                3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                String type = mDataList.get(position).getType();
                if (!TextUtils.isEmpty(type) && type.equals("福利")) {
                    return 1;
                }
                return 3;
            }
        });
        return layoutManager;
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        return new BaseRecyclerViewAdapter(getActivity().getApplicationContext()) {
            @Override
            protected int setItemViewType(int position) {
                GankCategoryData data = mDataList.get(position);
                if (TextUtils.isEmpty(data.get_id())) {
                    return 0;
                }

                switch (data.getType()) {
                    case ConstantUtils.ANDROID:
                    case ConstantUtils.APP:
                    case ConstantUtils.IOS:
                        return 1;
                    case ConstantUtils.VIDEO:
                    case ConstantUtils.EXPANDING_RES:
                    case ConstantUtils.RECOMMAND:
                        return 2;
                    case ConstantUtils.IMAGES:
                        return 3;
                }
                return 2;
            }

            @Override
            protected int getItemViewLayoutResID(int viewType) {
                switch (viewType) {
                    case 0:
                        return R.layout.item_category_title;
                    case 1:
                        return R.layout.item_category_web;
                    case 2:
                        return R.layout.item_category_desc;
                    case 3:
                        return R.layout.item_category_images;
                }
                return R.layout.item_category_desc;
            }

            @Override
            protected void bindByViewHolder(BaseViewHolder holder, int itemViewType, final int position) {
                switch (itemViewType) {
                    case 0:
                        holder.setText(R.id.tv_title, mDataList.get(position + 1).getType());
                        return;
                    case 1:
                        List<String> imageList = mDataList.get(position).getImages();
                        holder.setText(R.id.tv_desc, mDataList.get(position).getDesc());
                        holder.setText(R.id.tv_who, mDataList.get(position).getWho());
                        if (imageList != null) {
                            int listSize = imageList.size();
                            if (listSize >= 3) {
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(0),
                                        (ImageView) (holder.findViewById(R.id.iv_image_first)));
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(1),
                                        (ImageView) (holder.findViewById(R.id.iv_image_second)));
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(2),
                                        (ImageView) (holder.findViewById(R.id.iv_image_third)));
                                holder.findViewById(R.id.iv_image_first).setVisibility(View.VISIBLE);
                                holder.findViewById(R.id.iv_image_second).setVisibility(View.VISIBLE);
                                holder.findViewById(R.id.iv_image_third).setVisibility(View.VISIBLE);
                            } else if (listSize == 2) {
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(0),
                                        (ImageView) (holder.findViewById(R.id.iv_image_first)));
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(1),
                                        (ImageView) (holder.findViewById(R.id.iv_image_second)));
                                holder.findViewById(R.id.iv_image_first).setVisibility(View.VISIBLE);
                                holder.findViewById(R.id.iv_image_second).setVisibility(View.VISIBLE);
                                holder.findViewById(R.id.iv_image_third).setVisibility(View.GONE);
                            } else if (listSize == 1) {
                                ImageLoader.loadImage(HomeFragment.this.getActivity(), imageList.get(0),
                                        (ImageView) (holder.findViewById(R.id.iv_image_first)));
                                holder.findViewById(R.id.iv_image_first).setVisibility(View.VISIBLE);
                                holder.findViewById(R.id.iv_image_second).setVisibility(View.GONE);
                                holder.findViewById(R.id.iv_image_third).setVisibility(View.GONE);
                            }
                        }

                        holder.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString(WebViewActivity.BUNDLE_URL_TITLE, mDataList.get(position).getDesc());
                                bundle.putString(WebViewActivity.BUNDLE_CONTENT_URL, mDataList.get(position).getUrl());
                                HomeFragment.this.goToActivity(WebViewActivity.class, bundle);
                            }
                        });
                        return;
                    case 2:
                        holder.setText(R.id.tv_desc, mDataList.get(position).getDesc());
                        holder.setText(R.id.tv_who, mDataList.get(position).getWho());
                        holder.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString(WebViewActivity.BUNDLE_URL_TITLE, mDataList.get(position).getDesc());
                                bundle.putString(WebViewActivity.BUNDLE_CONTENT_URL, mDataList.get(position).getUrl());
                                HomeFragment.this.goToActivity(WebViewActivity.class, bundle);
                            }
                        });
                        return;
                    case 3:
                        ImageLoader.loadImage(HomeFragment.this.getActivity().getApplicationContext(),
                                mDataList.get(position).getUrl(), (ImageView) (holder.findViewById(R.id.iv_beautiful)));
                        holder.setOnClickListener(R.id.iv_beautiful, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString(PhotoActivity.BUNDLE_PHOTO_URL, mDataList.get(position).getUrl());
                            HomeFragment.this.goToActivity(PhotoActivity.class, bundle);
                        }
                    });

                }
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }
        };
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onPullToRefresh() {
        mPresenter.getHomeGankData();
    }

    @Override
    public void onLoadMore() {

    }
}
