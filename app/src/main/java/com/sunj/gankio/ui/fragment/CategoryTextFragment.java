package com.sunj.gankio.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunj.gankio.R;
import com.sunj.gankio.ui.activity.WebViewActivity;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/28 3:45 PM
 */

public class CategoryTextFragment extends CategoryFragment {

    public static Fragment newInstance(String s) {
        Fragment fragment = new CategoryTextFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_CATEGORY_TITLE, s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected RecyclerView.LayoutManager getListLayoutManager() {
        return new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void bindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int itemViewType, final int position) {
        holder.setText(R.id.tv_desc, mDataList.get(position).getDesc());
        holder.setText(R.id.tv_who, mDataList.get(position).getWho());

        holder.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("休息视频".equals(mCategory)) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataList.get(position).getUrl()));
                    startActivity(intent);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.BUNDLE_URL_TITLE, mDataList.get(position).getDesc());
                bundle.putString(WebViewActivity.BUNDLE_CONTENT_URL, mDataList.get(position).getUrl());
                CategoryTextFragment.this.goToActivity(WebViewActivity.class, bundle);
            }
        });
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemViewLayoutResID(int viewType) {
        return R.layout.item_category_desc;
    }
}
