package com.sunj.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 11:23 AM
 */ 

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    private LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseViewHolder.create(mInflater, parent, getItemViewLayoutResID(viewType));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindByViewHolder(holder, getItemViewType(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return setItemViewType(position);
    }

    protected abstract int setItemViewType(int position);

    protected abstract void bindByViewHolder(BaseViewHolder holder, int itemViewType, int position);

    protected abstract int getItemViewLayoutResID(int viewType);

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews = new SparseArray<>();

        public static BaseViewHolder create(LayoutInflater mInflater, ViewGroup parent, int itemLayoutResID) {
            return new BaseViewHolder(mInflater.inflate(itemLayoutResID, parent, false));
        }

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T findViewById(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public BaseViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            findViewById(id).setOnClickListener(listener);
            return this;
        }

        public BaseViewHolder setText(int id, String text) {
            TextView tv = findViewById(id);
            tv.setText(text);
            return this;
        }
    }
}
