package com.marsthink.progressbar;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhoumao on 2019/3/16.
 * Description:
 */
public class RingProgressBar extends View {

    private static final String TAG = "zhoumao";

    public final static int SECTOR = 0;
    public final static int LINE = 1;

    @IntDef({SECTOR, LINE})
    public @interface GraphMode {
    }

    @GraphMode
    private int mGraphMode;

    private final static int LINE_WIDTH = 10;

    private Paint mBarPaint;
    private Paint mNumPaint;

    private int mRadius;
    private int mProgressNum;

    public RingProgressBar(Context context) {
        this(context, null);
    }

    public RingProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mNumPaint = new Paint();
        mNumPaint.setTextSize(80);
        mNumPaint.setAntiAlias(true);
        mNumPaint.setStyle(Paint.Style.FILL);
        mNumPaint.setColor(Color.RED);

        mBarPaint = new Paint();
        mBarPaint.setStrokeWidth(LINE_WIDTH);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(Color.BLUE);
        mBarPaint.setMaskFilter(
                new BlurMaskFilter(LINE_WIDTH, BlurMaskFilter.Blur.SOLID));//设置模糊 ==阴影
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        print(widthMode, "widthMode");
//        print(heightMode, "heightMode");

        Log.d(TAG, "onMeasure: height: " + height + " width: " + width);
        mRadius = height < width ? height / 2 : width / 2;
        mRadius -= LINE_WIDTH;
    }

    private void print(int mode, String modeStr) {
        if (mode == MeasureSpec.AT_MOST) {
            Log.d(TAG, modeStr + " mode: MeasureSpec.AT_MOST");
        } else if (mode == MeasureSpec.EXACTLY) {
            Log.d(TAG, modeStr + " mode: MeasureSpec.EXACTLY");
        } else if (mode == MeasureSpec.UNSPECIFIED) {
            Log.d(TAG, modeStr + " mode: MeasureSpec.UNSPECIFIED");
        }
    }

    public void setProgressNum(int num) {
        mProgressNum = num;
        invalidate();
    }

    public void setGraphMode(@GraphMode int graphMode) {
        mGraphMode = graphMode;
        if (graphMode == LINE) {
            mBarPaint.setStyle(Paint.Style.STROKE);
        } else {
            mBarPaint.setStyle(Paint.Style.FILL);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF oval = new RectF(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius,
                getWidth() / 2 + mRadius, getHeight() / 2 + mRadius);
        if (mGraphMode == LINE) {
            canvas.drawArc(oval, -90, (float) mProgressNum / 100 * 360, false, mBarPaint);
        } else {
            canvas.drawArc(oval, -90, (float) mProgressNum / 100 * 360, true, mBarPaint);
        }

        Rect rect = new Rect();
        String progressStr = mProgressNum + "%";
        mNumPaint.getTextBounds(progressStr, 0, progressStr.length(), rect);
        canvas.drawText(progressStr, getWidth() / 2 - rect.width() / 2,
                getHeight() / 2 + rect.height() / 2, mNumPaint);
    }
}
