package com.marsthink.downloadmanager;

import android.os.Environment;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DownLoadConstants {

    public final static String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/" + "jamaljo/";

    public final static int START = 0;
    public final static int STOP = 1;
    public final static int COMPLETE = 2;
    public final static int FAILED = 3;
    public final static int CANCELED = 4;
    public final static int RUNNING = 5;

    @IntDef({
            DownLoadConstants.START,
            DownLoadConstants.STOP,
            DownLoadConstants.CANCELED,
            DownLoadConstants.FAILED,
            DownLoadConstants.COMPLETE,
            DownLoadConstants.RUNNING,
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface DownLoadState {
    }

    public final static int DOWNLOAD_SPLIT = 5;
}