package com.sunj.gankio.widget.option;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunj.gankio.R;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/9 4:33 PM
 */

public class OptionView extends ViewGroup {

    private int mGap = 30;
    private int mSelectedIndex = 0;
    private SparseArray<SparseArray<Integer>> mViewPositions = new SparseArray<>();
    private List<String> mTextList;
    private int mOptionCount;
    private OnOptionViewSelectedListener mListener;

    public OptionView(Context context) {
        super(context);
    }

    public OptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize - mGap * 2, MeasureSpec.EXACTLY);
        measureChildren(childWidthMeasureSpec, heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {

            int realHeight = mGap;
            int childWidth;
            int childHeight;
            int row = 1;
            int rowUsedWidth = mGap;
            for (int i=0; i<mOptionCount; i++) {
                View child = getChildAt(i);
                childWidth = child.getMeasuredWidth();
                childHeight = child.getMeasuredHeight();
                SparseArray<Integer> rowPositions = null;
                if ((mGap + rowUsedWidth + childWidth) < widthSize) {
                    rowUsedWidth = rowUsedWidth + mGap + childWidth;
                    rowPositions = mViewPositions.get(row);
                    if (rowPositions == null) {
                        mViewPositions.put(row, new SparseArray<Integer>());
                        rowPositions = mViewPositions.get(row);
                    }
                    rowPositions.put(i, i);
                } else {
                    realHeight = realHeight + mGap + childHeight;
                    row = row + 1;
                    rowUsedWidth = mGap + childWidth + mGap;

                    mViewPositions.put(row, new SparseArray<Integer>());
                    rowPositions = mViewPositions.get(row);
                    rowPositions.put(i, i);
                }
                if (i == (mOptionCount - 1)) {
                    realHeight = realHeight + mGap + childHeight;
                }
            }
            heightSize = Math.min(heightSize, realHeight);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = mGap;
        int top = mGap;
        for (int i=0; i<mViewPositions.size(); i++) {

            SparseArray<Integer> rowPositions = mViewPositions.valueAt(i);
            for (int j=0; j<rowPositions.size(); j++) {
                int viewIndex = rowPositions.valueAt(j);
                getChildAt(viewIndex).layout(left, top, left + getChildAt(viewIndex).getMeasuredWidth(),
                        top + getChildAt(viewIndex).getMeasuredHeight());
                left = left + mGap + getChildAt(viewIndex).getMeasuredWidth();
                if (j == (rowPositions.size() - 1)) {
                    top = top + mGap + getChildAt(viewIndex).getMeasuredHeight();
                }
            }
            left = mGap;
        }
    }

    public void setOnOptionViewSelectedListener(OnOptionViewSelectedListener mListener) {
        this.mListener = mListener;
    }

    public void setOptionTexts(List<String> stringArr) {
        mTextList = stringArr;
        mOptionCount = mTextList.size();
        for (String text : mTextList) {
            TextView tv = new TextView(getContext());
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray));
            tv.setPadding(25, 10, 25, 10);
            tv.setSingleLine(true);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newSelectedIndex = mTextList.indexOf(((TextView)v).getText());
                    changeCategory(newSelectedIndex);
                }
            });
            addView(tv, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        getChildAt(0).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void setSelectedIndex(int index) {
        changeCategory(index);
    }

    public void clear() {
        if (mSelectedIndex != -1) {
            getChildAt(mSelectedIndex).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray));
            mSelectedIndex = -1;
        }
    }

    private void changeCategory(int index) {

        if (index != mSelectedIndex) {
            if (mSelectedIndex != -1) {
                getChildAt(mSelectedIndex).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray));
            }
            if (index != -1) {
                getChildAt(index).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
            mSelectedIndex = index;
        }

        if (mListener != null) {
            mListener.onSelectedListener(mSelectedIndex);
        }
    }

    public interface OnOptionViewSelectedListener {
        void onSelectedListener(int index);
    }

}
