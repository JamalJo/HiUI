package com.marsthink.dragimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    private int mOriginWidth;
    private int mOriginHeight;

    private int mCurrentWidth;
    private int mCurrentHeight;

    private float mStartX;
    private float mStartY;

    private float mEndX;
    private float mEndY;

    private float mX;
    private float mY;

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
        options.inSampleSize = 8;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog, options);
        mCurrentHeight = mBitmap.getHeight();
        mCurrentWidth = mBitmap.getWidth();

        mX = mCurrentWidth / 2;
        mY = mCurrentHeight / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                if (mStartX > mEndX - mCurrentWidth / 2 && mStartX < mEndX + mCurrentWidth / 2
                        && mStartY > mEndY - mCurrentHeight / 2
                        && mStartY < mEndY + mCurrentHeight / 2) {
                    isStartValid = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isStartValid) {
                    if (Math.abs(event.getX() - mStartX) > 3 || Math.abs(event.getY() - mStartY)
                            > 3) {
                        mX = event.getX();
                        mY = event.getY();
                        Log.d(TAG, "onTouchEvent: " + mX + " " + mY);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredWidth();
        int width = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mX - mCurrentWidth / 2, mY - mCurrentHeight / 2, null);
    }
}
