package com.marsthink.dragimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhoumao on 2019/3/16.
 * Description:
 */
public class DragImageView extends View {

    private static final String TAG = "zhoumao";

    Bitmap mBitmap;

    private float mOriginWidth;
    private float mOriginHeight;

    private float mCurrentWidth;
    private float mCurrentHeight;

    private float mStartX;
    private float mStartY;

    private float mPreX;
    private float mPreY;

    private float mEndX;
    private float mEndY;

    private float mCenterX;
    private float mCenterY;

    private double mBaseVal;

    private double mOriginalVal;

    private boolean isStartValid = true;

    public DragImageView(Context context) {
        this(context, null);
    }

    public DragImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.dog, options);
        mOriginWidth = options.outWidth;
        mOriginHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        options.inSampleSize = 4;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog, options);
        mCurrentHeight = mBitmap.getHeight();
        mCurrentWidth = mBitmap.getWidth();

        mOriginWidth = mCurrentWidth;
        mOriginHeight = mCurrentHeight;

        mCenterX = mCurrentWidth / 2;
        mCenterY = mCurrentHeight / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                mPreX = event.getX();
                mPreY = event.getY();
                if (mStartX > mCenterX - mCurrentWidth / 2 && mStartX < mCenterX + mCurrentWidth / 2
                        && mStartY > mCenterY - mCurrentHeight / 2
                        && mStartY < mCenterY + mCurrentHeight / 2) {
                    isStartValid = true;

                }
                mBaseVal = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    if (isStartValid) {
                        if (Math.abs(event.getX() - mStartX) > 3
                                || Math.abs(event.getY() - mStartY) > 3) {
                            mCenterX = mCenterX + event.getX() - mPreX;
                            mCenterY = mCenterY + event.getY() - mPreY;
                            Log.d(TAG, "onTouchEvent: " + mCenterX + " " + mCenterY);
                            invalidate();
                        }
                    }
                    mPreX = event.getX();
                    mPreY = event.getY();
                } else if (event.getPointerCount() == 2) {
                    float x = event.getX(0) - event.getX(1);
                    float y = event.getY(0) - event.getY(1);
                    double val = Math.sqrt(x * x + y * y);
                    if (mBaseVal == 0) {
                        mBaseVal = val;
                    } else {
                        float scale = (float) (val / mBaseVal);
                        setScale(scale);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isStartValid) {
                    mEndX = event.getX();
                    mEndY = event.getY();
                }
                isStartValid = false;
                break;
        }
        return true;
    }

    private void setScale(float scale) {
        mCurrentWidth = mCurrentWidth * scale;

        mCurrentHeight = mCurrentHeight * scale;

        // 控制阈值，防止放大缩小过快；
        if (mCurrentHeight / mOriginHeight > 2) {
            mCurrentHeight = mOriginHeight * 2;
            mCurrentWidth = mOriginWidth * 2;
        }
        if (mCurrentWidth / mOriginHeight < 0.5) {
            mCurrentHeight = mOriginHeight * 0.5f;
            mCurrentWidth = mOriginWidth * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF destRectF = new RectF(mCenterX - mCurrentWidth / 2, mCenterY - mCurrentHeight / 2,
                mCenterX + mCurrentWidth / 2, mCenterY + mCurrentHeight / 2);
        canvas.drawBitmap(mBitmap, null, destRectF, null);
    }
}
