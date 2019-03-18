package com.marsthink.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by zhoumao on 2019/c/18.
 * Description:
 */
public class ViewPager extends ViewGroup {

    private static final String TAG = "zhoumao";

    private float mStartScrollX;
    private float mLastX;

    private Scroller mScroller;

    private int mPagingTouchSlop;

    private int mScreenWidth;

    public ViewPager(Context context) {
        this(context, null);
    }

    public ViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(getContext());
    }

    private void init(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        mScreenWidth = displayMetrics.widthPixels;
        ViewConfiguration viewConfiguration = new ViewConfiguration();
        mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        mScroller = new Scroller(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartScrollX = getScrollX();
                mLastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                float mMoveVal = event.getX() - mLastX;
                if (getScrollX() + mMoveVal < 0) {
                    mMoveVal = 0;
                } else if (getScrollX() + mMoveVal > getWidth() + mScreenWidth) {
                    mMoveVal = 0;
                }
                scrollBy((int) mMoveVal, 0);
//                mScroller.startScroll(getScrollX(),0,(int) mMoveVal,0);  // 为什么不能用这个实现？  有duration
                invalidate();
                mLastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float delta = getScrollX() - mStartScrollX;
                if (delta > mPagingTouchSlop) {
                    mScroller.startScroll(getScrollX(), 0, (int) (mScreenWidth
                            - delta), 0);
                } else if (delta < -mPagingTouchSlop) {
                    mScroller.startScroll(getScrollX(), 0, (int) (-mScreenWidth
                            - delta), 0);
                } else {
                    mScroller.startScroll(getScrollX(), 0, (int) (-delta), 0);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) {
            return;
        }
        int count = getChildCount();
        MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        layoutParams.width = mScreenWidth * count;
        for (int i = 0; i < count; i++) {
            getChildAt(i).layout(i * mScreenWidth, 0, (1 + i) * mScreenWidth, b);
        }
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
}
