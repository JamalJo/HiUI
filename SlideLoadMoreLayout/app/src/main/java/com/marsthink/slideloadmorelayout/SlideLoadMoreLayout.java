package com.marsthink.slideloadmorelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class SlideLoadMoreLayout extends LinearLayout {

    private View mSlideView;

    private RecyclerView mRecyclerView;

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
        mSlideView = findViewById(R.id.view_slide);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
