package com.haha.roundrelativelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhoumao on 2019/3/26.
 * Description:
 */
public class XferModeView extends View {

    private Paint paint;

    public XferModeView(Context context) {
        this(context, null);
    }

    public XferModeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XferModeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0xFFFFCC44);
        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        int r = getWidth() / 3;
        //正常绘制黄色的圆形
        paint.setColor(0xFFFFCC44);
        canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setColor(0xFF66AAFF);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
