package com.marsthink.scratchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhoumao on 2019/3/16.
 * Description: 自定义View--刮刮卡 https://blog.csdn.net/qq_30716173/article/details/51122474
 */
public class ScratchView extends View {

    private static final String TAG = "zhoumao";
    private Path mPath;

    private Bitmap mScratchBitmap;

    private Paint mScratchPaint;

    private final static int SCRATCH_WIDTH = 60;

    private Paint mPrizePaint;
    private int mPrizeWidth;
    private int mPrizeHeight;

    private float mStartX = 0;
    private float mStartY = 0;

    private Canvas mScratchCanvas;

    private boolean isScratchedOut = true;

    private Xfermode mClearXfermode;

    private final static int CRITICAL_VAL = 50;

    private Bitmap mComputeBitMap;
    private Canvas mComputeCanvas;

    private String mPrize;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        isScratchedOut = false;
        mClearXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    private void init() {
        mPath = new Path();

        mScratchPaint = new Paint();
        mScratchPaint.setColor(ContextCompat.getColor(getContext(), R.color.orange));
        mScratchPaint.setAntiAlias(true);

        mPrizePaint = new Paint();
        mPrizePaint.setStyle(Paint.Style.STROKE);
        mPrizePaint.setTextSize(100);
        mPrizePaint.setColor(Color.RED);
    }

    public void setPrize(String prize) {
        this.mPrize = prize;
        Rect rect = new Rect();
        mPrizePaint.getTextBounds(mPrize, 0, mPrize.length(), rect);
        mPrizeWidth = rect.width();
        mPrizeHeight = rect.height();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                mPath.moveTo(mStartX, mStartY);
                Log.d(TAG, "onTouchEvent: " + event.getX() + "  " + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getY() - mStartY) > 3 || Math.abs(event.getX() - mStartX) > 3) {
                    mPath.lineTo(event.getX(), event.getY());
//                    Log.d(TAG, "onTouchEvent: " + event.getX() + "  " + event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                int percent = computeScratchPercent();
                if (percent >= CRITICAL_VAL) {
                    isScratchedOut = true;
                    break;
                }
        }
        return true;
    }

    // 计算刮开的面积
    private int computeScratchPercent() {
        if (mComputeBitMap == null) {
            return 0;
        }
        int[] arr = new int[mComputeBitMap.getWidth() * mComputeBitMap.getHeight()];
        mComputeBitMap.getPixels(arr, 0, mComputeBitMap.getWidth(), 0, 0, mComputeBitMap.getWidth(),
                mComputeBitMap.getHeight());
        int clearCount = 0;
        for (int i : arr) {
            if (i == 0) {
                clearCount++;
            }
        }
        return clearCount * 100 / arr.length;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mScratchBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mComputeBitMap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);

        mScratchCanvas = new Canvas(mScratchBitmap);
        mScratchCanvas.drawColor(ContextCompat.getColor(getContext(), R.color.grey));

        mComputeCanvas = new Canvas(mComputeBitMap);
        mComputeCanvas.drawColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPrize == null || mPrize.isEmpty()) {
            return;
        }

        canvas.drawText(mPrize, getWidth() / 2 - mPrizeWidth / 2,
                getHeight() / 2 + mPrizeHeight / 2,
                mPrizePaint);

        if (isScratchedOut) {
            return;
        }
        mScratchPaint.setStyle(Paint.Style.STROKE);
        mScratchPaint.setStrokeWidth(SCRATCH_WIDTH);
        mScratchPaint.setXfermode(mClearXfermode);  //Mode.DST_OUT改模式就类似橡皮檫，这个属性设置是关键

        mScratchCanvas.drawPath(mPath, mScratchPaint);
        mComputeCanvas.drawPath(mPath, mScratchPaint);

        canvas.drawBitmap(mScratchBitmap, 0, 0, null);
    }
}
