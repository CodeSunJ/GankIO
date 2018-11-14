package com.sunj.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sunj.gankio.R;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.activity.WebViewActivity;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/28 3:21 PM
 */

public class CategoryWebFragment extends CategoryFragment {

    public static Fragment newInstance(String s) {
        Fragment fragment = new CategoryWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_CATEGORY_TITLE, s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void bindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int itemViewType, final int position) {
        final List<String> imageList = mDataList.get(position).getImages();
        holder.setText(R.id.tv_desc, mDataList.get(position).getDesc());
        holder.setText(R.id.tv_who, mDataList.get(position).getWho());

        if (imageList != null) {
            int listSize = imageList.size();
            if (listSize >= 3) {
                ImageLoader.loadImage(this.getActivity(), imageList.get(0),
                        (ImageView) (holder.findViewById(R.id.iv_image_first)));
                ImageLoader.loadImage(this.getActivity(), imageList.get(1),
                        (ImageView) (holder.findViewById(R.id.iv_image_second)));
                ImageLoader.loadImage(this.getActivity(), imageList.get(2),
                        (ImageView) (holder.findViewById(R.id.iv_image_third)));
                holder.findViewById(R.id.iv_image_first).setVisibility(View.VISIBLE);
                holder.findViewById(R.id.iv_image_second).setVisibility(View.VISIBLE);
                holder.findViewById(R.id.iv_image_third).setVisibility(View.VISIBLE);
            } else if (listSize == 2) {
                ImageLoader.loadImage(this.getActivity(), imageList.get(0),
                        (ImageView) (holder.findViewById(R.id.iv_image_first)));
                ImageLoader.loadImage(this.getActivity(), imageList.get(1),
                        (ImageView) (holder.findViewById(R.id.iv_image_second)));
                holder.findViewById(R.id.iv_image_first).setVisibility(View.VISIBLE);
                holder.findViewById(R.id.iv_image_second).setVisibility(View.VISIBLE);
                holder.findViewById(R.id.iv_image_third).setVisibility(View.GONE);
            } else if (listSize == 1) {
                ImageLoader.loadImage(this.getActivity(), imageList.get(0),
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
                CategoryWebFragment.this.goToActivity(WebViewActivity.class, bundle);
            }
        });

    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemViewLayoutResID(int viewType) {
        return R.layout.item_category_web;
    }
}
