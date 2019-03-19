package com.marsthink.scrolltablayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhoumao on 2019/3/18.
 * Description:
 */
public class TabLayout extends HorizontalScrollView {

    private static final String TAG = "zhoumao";

    int mIndex;

    int mLastIndex;

    private LinearLayout mLinearLayout;
    private FrameLayout mMainLayout;
    private View mTabIndicator;
    private Context mContext;

    private int TAB_INDICATOR_SUB_VAL = 60;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mMainLayout = new FrameLayout(context);
        mMainLayout.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMainLayout);

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        mTabIndicator = new View(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(50, 5,
                Gravity.BOTTOM);
        mTabIndicator.setPadding(0, 20, 0, 0);
        mTabIndicator.setLayoutParams(layoutParams);
        mTabIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

        mMainLayout.addView(mLinearLayout);
        mMainLayout.addView(mTabIndicator);
        mContext = context;
    }

    public void addTab(String tabStr) {
        final TextView tabTv = new TextView(mContext);
        tabTv.setPadding(20, 0, 20, 0);
        tabTv.setText(tabStr);
        tabTv.setTextSize(25);
        tabTv.setTag(mIndex);
        tabTv.setTextColor(ContextCompat.getColor(mContext, R.color.grey));
        tabTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                scrollTo(index);
                mLastIndex = index;
            }
        });
        mIndex++;
        mLinearLayout.addView(tabTv);
        mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(
                                this); //需要取消listener
                        scrollTo(0);
                        return false;
                    }
                });
    }


    private void scrollTo(int pos) {
        changeTabState(pos);

        View selectView = mLinearLayout.getChildAt(pos);
        int targetScrollX = selectView.getLeft() + selectView.getWidth() / 2 - getWidth() / 2;
        smoothScrollTo(targetScrollX, 0);

        startIndicatorAnimation(selectView);
    }

    private void changeTabState(int pos) {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            TextView textView = (TextView) mLinearLayout.getChildAt(i);
            if (pos == i) {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.grey));
            }
        }
    }

    private void startIndicatorAnimation(View selectView) {
        float fromX = mTabIndicator.getX();
        float targetX = selectView.getX() + TAB_INDICATOR_SUB_VAL / 2;

        ArrayList<Animator> animatorList = new ArrayList<>();
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator moveAnimator = ValueAnimator.ofFloat(fromX, targetX);
        moveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTabIndicator.setX((float) animation.getAnimatedValue());
            }
        });
        animatorList.add(moveAnimator);

        ValueAnimator widthAnimator = ValueAnimator.ofInt(mTabIndicator.getWidth(),
                selectView.getWidth() - TAB_INDICATOR_SUB_VAL);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTabIndicator.getLayoutParams().width = (int) animation.getAnimatedValue();
                mTabIndicator.requestLayout();
            }
        });
        animatorList.add(widthAnimator);

        animatorSet.playTogether(animatorList);
        animatorSet.start();
    }
}
