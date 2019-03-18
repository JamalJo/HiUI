package com.marsthink.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhoumao on 2019/3/17.
 * Description:
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "zhoumao";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            for (int i = 0; i < getChildCount(); i++) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
            return;
        }
        int sumHeight = 0;
        int sumWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            sumWidth += child.getMeasuredWidth();
            if (sumWidth > getMeasuredWidth()) {
                sumWidth = child.getMeasuredWidth();
                sumHeight += maxHeight;
                maxHeight = 0;
            }
            if (maxHeight < child.getMeasuredHeight()) {
                maxHeight = child.getMeasuredHeight();
            }
            if (i == getChildCount() - 1) {
                sumHeight += maxHeight;
            }
        }
        setMeasuredDimension(width, sumHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int sumWidth = 0;
        int maxHeight = 0;
        int sumHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(sumWidth, sumHeight, sumWidth + child.getMeasuredWidth(),
                    sumHeight + child.getMeasuredHeight());

            sumWidth += child.getMeasuredWidth();
            if (sumWidth > getMeasuredWidth()) {
                sumWidth = 0;
                i--;
                sumHeight += maxHeight;
                maxHeight = 0;
            }
            if (maxHeight < child.getMeasuredHeight()) {
                maxHeight = child.getMeasuredHeight();
            }
        }
    }
}
