package com.fan.collapsinglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huisoucw on 2018/9/19.
 */

public class CollapsingLayout extends ViewGroup {
    private View mCollapsingView;
    private int mOriginalHeight;
    private View mScrollableView;

    public CollapsingLayout(Context context) {
        this(context, null);
    }

    public CollapsingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(l, top, r, top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        if (mOriginalHeight == 0)
            mOriginalHeight = getChildAt(0).getMeasuredHeight();

    }

    private float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = ev.getRawY();
                float dy = curY - mLastY;
                mLastY = curY;
                if (dy > 0) {
                    if (mScrollCallback.canChildScroll(-1)) {
                        return super.dispatchTouchEvent(ev);
                    }
                }
                float collapsingViewCurTranslateY = mCollapsingView.getTranslationY();
                float scrollableViewCurTranslateY = mScrollableView.getTranslationY();
                mCollapsingView.setTranslationY(collapsingViewCurTranslateY + dy / 2);
                mScrollableView.setTranslationY(scrollableViewCurTranslateY + dy);
                if (-mScrollableView.getTranslationY() >= mOriginalHeight || -mCollapsingView.getTranslationY() >= mOriginalHeight/2) {
                    mScrollableView.setTranslationY(-mOriginalHeight);
                    mCollapsingView.setTranslationY(-mOriginalHeight/2);
                }

                if (mScrollableView.getTranslationY() > 0 || mScrollableView.getTranslationY() > 0) {
                    mScrollableView.setTranslationY(0);
                    mCollapsingView.setTranslationY(0);
                    return super.dispatchTouchEvent(ev);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new InflateException("you can only have 2 child");
        }
        mCollapsingView = getChildAt(0);
        mScrollableView = getChildAt(1);
    }

    OnScrollCallback mScrollCallback;

    public void setOnScrollCallback(OnScrollCallback callback) {
        mScrollCallback = callback;
    }

    public interface OnScrollCallback {
        boolean canChildScroll(int direction);
    }
}
