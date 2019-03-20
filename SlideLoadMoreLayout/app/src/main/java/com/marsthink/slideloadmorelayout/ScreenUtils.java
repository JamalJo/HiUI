package com.marsthink.slideloadmorelayout;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class ScreenUtils {

    public static int getWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }
}
