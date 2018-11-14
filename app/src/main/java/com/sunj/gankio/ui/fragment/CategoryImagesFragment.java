package com.sunj.gankio.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.sunj.gankio.R;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.activity.PhotoActivity;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/28 11:26 AM
 */

public class CategoryImagesFragment extends CategoryFragment {

    public static Fragment newInstance(String s) {
        Fragment fragment = new CategoryImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_CATEGORY_TITLE, s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void bindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int itemViewType, final int position) {
        ImageLoader.loadImage(CategoryImagesFragment.this.getActivity().getApplicationContext(),
                mDataList.get(position).getUrl(), (ImageView) (holder.findViewById(R.id.iv_beautiful)));
        holder.setOnClickListener(R.id.iv_beautiful, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CategoryImagesFragment.this.getActivity(), PhotoActivity.class);
                intent.putExtra(PhotoActivity.BUNDLE_PHOTO_URL, mDataList.get(position).getUrl());
                CategoryImagesFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemViewLayoutResID(int viewType) {
        return R.layout.item_category_images;
    }
}
