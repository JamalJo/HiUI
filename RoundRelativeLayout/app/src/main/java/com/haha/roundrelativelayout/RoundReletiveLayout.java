package com.haha.roundrelativelayout;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zhoumao on 2019/3/26.
 * Description:
 */
public class RoundReletiveLayout extends RelativeLayout {

    private Paint mPaint;

    private final static float STROKE_WIDTH = 1;

    public RoundReletiveLayout(Context context) {
        this(context, null);
    }

    public RoundReletiveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundReletiveLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.round, this);
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mPaint.setAntiAlias(true);
    }

    private static final String TAG = "zhoumao";

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 保存
        int layoutId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, ALL_SAVE_FLAG);

        // 画背景色
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.orange));

        // 完成所有子view的绘制
        super.dispatchDraw(canvas);

        //画交错
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int mRadius = centerX;
        RectF oval = new RectF(0, 0, centerX + mRadius, centerY + mRadius);
        Path path = new Path();
        float[] radii = {0, 0, 50, 50, 50, 50, 0, 0};
        path.addRoundRect(oval, radii, Path.Direction.CCW);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(path, mPaint);

        // 画边线
        mPaint.setXfermode(null);
        RectF lineOval = new RectF(STROKE_WIDTH / 2, STROKE_WIDTH / 2,
                centerX + mRadius - (STROKE_WIDTH / 2), centerY + mRadius - (STROKE_WIDTH / 2));
        Path linePath = new Path();
        linePath.addRoundRect(lineOval, radii, Path.Direction.CCW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        canvas.drawPath(linePath, mPaint);

        // 恢复
        canvas.restoreToCount(layoutId);
    }
}
