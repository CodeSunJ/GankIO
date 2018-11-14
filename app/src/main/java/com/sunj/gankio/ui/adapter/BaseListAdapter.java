package com.sunj.gankio.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/27 5:45 PM
 */

public abstract class BaseListAdapter extends BaseAdapter {

    private Context mContext;

    public BaseListAdapter(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemViewLayoutResID(), parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        vh = (ViewHolder) convertView.getTag();
        bindData(vh, position);
        return convertView;
    }

    protected abstract void bindData(ViewHolder vh, int position);

    protected abstract int getItemViewLayoutResID();

    public static class ViewHolder {
        private View mConvertView;
        private SparseArray<View> mViews = new SparseArray<>();

        public ViewHolder(View mView) {
            this.mConvertView = mView;
        }

        public <T extends View> T findViewById(int resID) {
            View view = mViews.get(resID);
            if (view == null) {
                view = mConvertView.findViewById(resID);
                mViews.put(resID, view);
            }
            return (T) view;
        }

    }
}
