package com.sunj.gankio.widget.refresh;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.sunj.gankio.R;
import com.sunj.gankio.ui.adapter.BaseRecyclerViewAdapter;
import com.sunj.gankio.util.DisplayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/18 4:17 PM
 */

public class RefreshRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private static final int CONSTANT_INITIAL_STATE = 0;
    private static final int CONSTANT_PULL_TO_REFRESH_STATE = 1;
    private static final int CONSTANT_LOAD_MORE_STATE = 2;

    private int mState = CONSTANT_INITIAL_STATE;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private OnListItemRefreshListener mListener;

    private boolean isSupportPullToRefresh = false;
    private boolean isSupportLoadMore = false;

    public RefreshRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_refresh_recycler_view, this, true);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(false);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSupportLoadMore) {
                    RecyclerView.LayoutManager layout = recyclerView.getLayoutManager();
                    boolean isReturnPositionArray = false;
                    int lastPosition = -1;
                    Method method = null;
                    try {
                        method = layout.getClass().getDeclaredMethod("findLastVisibleItemPosition", null);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (method == null) {
                        try {
                            method = layout.getClass().getDeclaredMethod("findLastVisibleItemPositions", int[].class);
                            isReturnPositionArray = true;
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                    if (method != null) {
                        int[] lastPositionArray = null;
                        try {
                            if (!isReturnPositionArray) {
                                lastPosition = (int) method.invoke(layout, null);
                            } else {
                                lastPositionArray = (int[]) method.invoke(layout, new Object[]{lastPositionArray});
                                if (lastPositionArray.length >= 2) {
                                    lastPosition = Math.max(lastPositionArray[0], lastPositionArray[1]);
                                } else {
                                    lastPosition = lastPositionArray[0];
                                }
                            }
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    if (lastPosition != -1) {
                        int totalItemCount = recyclerView.getAdapter().getItemCount();
                        if ((totalItemCount - lastPosition) < 5) {
                            if (mListener != null && mState == CONSTANT_INITIAL_STATE) {
                                mState = CONSTANT_LOAD_MORE_STATE;
                                enablePullToRefresh(false);
                                mListener.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        if (mListener != null) {
            mState = CONSTANT_PULL_TO_REFRESH_STATE;
            mSwipeRefreshLayout.setRefreshing(true);
            mListener.onPullToRefresh();
        }
    }

    public void enablePullToRefresh() {
        isSupportPullToRefresh = true;
    }

    public void enableLoadMore() {
        isSupportLoadMore = true;
    }

    public void enablePullToRefresh(boolean enabled) {
        if (!isSupportPullToRefresh)
            return;
        if (enabled && mState == CONSTANT_LOAD_MORE_STATE)
            return;
        mSwipeRefreshLayout.setEnabled(enabled);
    }

    public void pullToRefresh() {
        if (isSupportPullToRefresh) {
            mState = CONSTANT_PULL_TO_REFRESH_STATE;
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public void completeRefresh() {
        switch (mState) {
            case CONSTANT_LOAD_MORE_STATE:
                mState = CONSTANT_INITIAL_STATE;
                enablePullToRefresh(true);
                break;
            case CONSTANT_PULL_TO_REFRESH_STATE:
                mSwipeRefreshLayout.setRefreshing(false);
                mState = CONSTANT_INITIAL_STATE;
                break;
        }
    }

    public boolean isLoadMore() {
        return mState == CONSTANT_LOAD_MORE_STATE;
    }

    public void setAdapter(BaseRecyclerViewAdapter mAdapter) {
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setRefreshListener(OnListItemRefreshListener listener) {
        mListener = listener;
    }

    public void cancelRefreshListener() {
        mListener = null;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration == null) {
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.left = DisplayUtils.dp2px(getContext(), 3);
                    outRect.right = DisplayUtils.dp2px(getContext(), 3);
                    outRect.top = DisplayUtils.dp2px(getContext(), 3);
                    outRect.bottom = DisplayUtils.dp2px(getContext(), 3);
                }
            });
        }
    }

    public interface OnListItemRefreshListener {
        void onPullToRefresh();
        void onLoadMore();
    }
}
