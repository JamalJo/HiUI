package com.haha.overlaypicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhoumao on 2019/3/30.
 * Description:
 */
public class OverlayPictureView extends View {

    private Paint mPaint;
    private Drawable mBottomDrawable;
    private Bitmap mTopBitmap;

    private double mRadio;

    public OverlayPictureView(Context context) {
        this(context, null);
    }

    public OverlayPictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayPictureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mBottomDrawable = ContextCompat.getDrawable(getContext(), R.drawable.b);
        mTopBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mBottomDrawable.setBounds(new Rect(0, 0, getWidth(), getHeight()));
    }

    public void setRadio(double radio) {
        mRadio = radio;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBottomDrawable.draw(canvas);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.ALL_SAVE_FLAG);
        int width = (int) (getWidth() * 2 * mRadio);
        int height = (int) (getHeight() * 2 * mRadio);
        RectF rectF = new RectF(getWidth() - width, getHeight() - height, getWidth() + width,
                getHeight() + height);
        canvas.drawOval(rectF, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mTopBitmap, 0, 0, mPaint);
        canvas.restoreToCount(sc);
        mPaint.setXfermode(null);
    }

}
