package com.sunj.gankio.widget.search;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sunj.gankio.R;


/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/28 10:02 PM
 */

public class SearchText extends AppCompatEditText {

    private Drawable mRightDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_delete);

    private float top, bottom, left, right, movex, movey;

    public SearchText(Context context) {
        super(context);
    }

    public SearchText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void showDeleteIcon(boolean isShow) {
        if (isShow) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setPadding(getHeight()/2, 0, getHeight()/2-mRightDrawable.getBounds().width()/2, 0);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        showDeleteIcon(text.length() != 0 && hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        showDeleteIcon(length() != 0 && focused);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                movex = event.getX();
                movey = event.getY();
                bottom = getHeight();
                left = getWidth() - getPaddingRight() - mRightDrawable.getBounds().width();
                right = getWidth();
                if (length() != 0 && movex > left && movex < right && movey > top && movey < bottom) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
