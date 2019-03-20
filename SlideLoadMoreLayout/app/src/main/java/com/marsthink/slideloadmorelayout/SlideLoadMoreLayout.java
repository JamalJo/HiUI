package com.marsthink.slideloadmorelayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class SlideLoadMoreLayout extends LinearLayout {

    private static final String TAG = "zhoumao";

    private View mSlideView;

    private float mLastX;

    private RecyclerView mRecyclerView;

    private boolean enableSlide;

    private float mRecyclerViewOriginX;
    private float mSlideViewOriginX;

    private boolean isMoved;

    private OnSlidePullListener mOnSlidePullListener;


    public SlideLoadMoreLayout(Context context) {
        this(context, null);
    }

    public SlideLoadMoreLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLoadMoreLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(getContext(), R.layout.layout_slide_load_more, this);
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        mSlideView = findViewById(R.id.view_slide);
        mRecyclerView = findViewById(R.id.recycle_view);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setOnSlidePullListener(OnSlidePullListener onSlidePullListener) {
        mOnSlidePullListener = onSlidePullListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mRecyclerViewOriginX = mRecyclerView.getX();
        mSlideViewOriginX = mSlideView.getX();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        updateSlideState();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                mLastX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float scrollX = ev.getX() - mLastX;
                if (enableSlide && scrollX < 0) { // 左滑且recyclerView已经到最后一个item情况下
                    int offset = (int) (scrollX * 0.6);
                    moveAllViews(offset);
                    isMoved = true;
                    if (mOnSlidePullListener != null) {
                        mOnSlidePullListener.onStart();
                    }
                    return true;
                } else {
                    mLastX = ev.getX();
                    isMoved = false;
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                if (isMoved) {
                    completeToUpload();
                    if (mOnSlidePullListener != null) {
                        mOnSlidePullListener.onFingerReleased();
                    }
                    return true;
                } else {
                    return super.dispatchTouchEvent(ev);
                }

            default:
                return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 完成更新调用
     */
    private void completeToUpload() {

        postDelayed(new Runnable() {
            @Override
            public void run() {
                moveAllViews(0);
            }
        }, 200);

    }

    private void moveAllViews(float dx) {
        mRecyclerView.setX((int) (mRecyclerViewOriginX + dx));
        mSlideView.setX((int) (mSlideViewOriginX + dx));
    }

    private void updateSlideState() {
        LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        if (manager == null) {
            return;
        }
        if (manager.findLastCompletelyVisibleItemPosition() == manager.getItemCount() - 1) {
            enableSlide = true;
        } else {
            enableSlide = false;
        }
    }

    public interface OnSlidePullListener {
        void onStart();

        void onFingerReleased();
    }
}
