package com.indusfo.spc.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.indusfo.spc.R;

/**
 * Created by Bruce on 11/24/14.
 */
public class SwipeLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View contentView;
    private View actionView;
    private int dragDistance;
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int draggedX;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        actionView = getChildAt(1);
        actionView.setVisibility(GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        dragDistance = actionView.getMeasuredWidth();
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == contentView || view == actionView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if (changedView == contentView) {
                actionView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            if (actionView.getVisibility() == View.GONE) {
                actionView.setVisibility(View.VISIBLE);
            }
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        //不重写该方法默认返回0，返回0时若还设置了点击事件则水平方向不能移动
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
//            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        //不重写该方法默认返回0，返回0时若还设置了点击事件则竖直方向不能移动
//        @Override
//        public int getViewVerticalDragRange(View child) {
//            return getMeasuredHeight() - child.getMeasuredHeight();
//        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if (xvel > AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = true;
            } else if (draggedX <= -dragDistance / 2) {
                settleToOpen = true;
            } else if (draggedX > -dragDistance / 2) {
                settleToOpen = false;
            }

            final int settleDestX = settleToOpen ? -dragDistance : 0;
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if(viewDragHelper.shouldInterceptTouchEvent(ev)
                || ev.getAction() == MotionEvent.ACTION_CANCEL
                || ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        boolean flag = super.onInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);*/
        if (ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            event.setEdgeFlags(1);
            viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }


}
