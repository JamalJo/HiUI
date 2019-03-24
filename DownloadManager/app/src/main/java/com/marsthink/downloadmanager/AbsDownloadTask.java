package com.marsthink.downloadmanager;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public abstract class AbsDownloadTask implements Runnable {
    private static final String TAG = "zhoumao";

    protected String mDownloadUrl;

    protected DownloadListener mDownloadListener;

    @Override
    public void run() {
        if (mDownloadListener != null) {
            mDownloadListener.onStart();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(mDownloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                long cur = System.currentTimeMillis();
                inputStream = connection.getInputStream();
                byte[] buffer = new byte[4096];
                outputStream = new FileOutputStream(
                        DownLoadConstants.DOWNLOAD_PATH + getFileName());
                int len = inputStream.read(buffer);
                while (len > 0) {
                    outputStream.write(buffer, 0, len);
                    len = inputStream.read(buffer);
                    if (mDownloadListener != null) {
                        mDownloadListener.onRuning();
                    }
                }
                outputStream.flush();
                Log.d(TAG, "*******************run: " + (System.currentTimeMillis() - cur));
                if (mDownloadListener != null) {
                    mDownloadListener.onComplete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (mDownloadListener != null) {
                mDownloadListener.onFailed();
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (mDownloadListener != null) {
                    mDownloadListener.onFailed();
                }
            }
        }
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    protected abstract String getFileName();
}
