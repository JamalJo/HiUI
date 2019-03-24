package com.marsthink.downloadmanager;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public abstract class DownloadListener {
    public void onStart(long totalBytes) {
    }

    public void onWait() {
    }

    public void onStop() {
    }

    public void onCancel() {
    }

    public void onFailed() {
    }

    public void onComplete(String destPath) {
    }

    public void onRuning(long curBytes, long totalBytes) {
    }
}
