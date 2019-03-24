package com.marsthink.downloadmanager;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
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

    protected long mStart;
    protected long mEnd;

    protected boolean isPartDownload = false;

    @Override
    public void run() {
        if (isPartDownload) {
            doPartDownload();
            return;
        }
        if (mDownloadListener != null) {
            mDownloadListener.onWait();
        }

        try {
            URL url = new URL(mDownloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Range", "bytes=" + 0 + "-");
            connection.setInstanceFollowRedirects(true);

            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {                // 直接下载
                doDirectDownload(connection);
            } else if (code == HttpURLConnection.HTTP_PARTIAL) {    // 断点续传
                splitDownload(connection);
            } else {
                if (mDownloadListener != null) {
                    mDownloadListener.onFailed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (mDownloadListener != null) {
                mDownloadListener.onFailed();
            }
        }
    }

    private void doPartDownload() {
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        try {
            URL url = new URL(mDownloadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Range", "bytes=" + mStart + "-" + (mEnd - 1));
            conn.setConnectTimeout(5000);
            conn.setInstanceFollowRedirects(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                if (mDownloadListener != null) {
                    mDownloadListener.onStart(0);
                }
                inputStream = conn.getInputStream();

                File file = new File(getFilePath());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(mStart);
                long sumLen = 0;
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                while (len > 0) {
                    sumLen += len;
                    randomAccessFile.write(buffer, 0, len);
                    len = inputStream.read(buffer);
                    if (mDownloadListener != null) {
                        mDownloadListener.onRuning(0, 0);
                    }
                }
            } else {
                if (mDownloadListener != null) {
                    mDownloadListener.onFailed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (mDownloadListener != null) {
                    mDownloadListener.onFailed();
                }
            }
        }
    }

    private void doDirectDownload(HttpURLConnection connection) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        long contentLength = connection.getContentLength();
        if (mDownloadListener != null) {
            mDownloadListener.onStart(contentLength);
        }
        try {
            long cur = System.currentTimeMillis();
            inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            outputStream = new FileOutputStream(getFilePath());
            int len = inputStream.read(buffer);
            while (len > 0) {
                outputStream.write(buffer, 0, len);
                len = inputStream.read(buffer);
                if (mDownloadListener != null) {
                    mDownloadListener.onRuning(0, 0);
                }
            }
            outputStream.flush();
            Log.d(TAG, "*******************run: " + (System.currentTimeMillis() - cur));
            if (mDownloadListener != null) {
                mDownloadListener.onComplete(getFilePath());
            }
        } catch (Exception e) {
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

    private void splitDownload(HttpURLConnection connection) {
        long contentLength = connection.getContentLength();
        if (mDownloadListener != null) {
            mDownloadListener.onStart(contentLength);
        }
        long partSize = contentLength / DownLoadConstants.DOWNLOAD_SPLIT;
        try {
            File file = new File(getFilePath());
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.setLength(contentLength);
            for (int i = 0; i < DownLoadConstants.DOWNLOAD_SPLIT - 1; i++) {
                randomAccessFile.seek(i * partSize);
                DownloadManager.getInstance().download(mDownloadUrl, i * partSize,
                        (i + 1) * partSize,
                        mDownloadListener);
            }
            randomAccessFile.seek((DownLoadConstants.DOWNLOAD_SPLIT - 1) * partSize);
            DownloadManager.getInstance().download(mDownloadUrl,
                    (DownLoadConstants.DOWNLOAD_SPLIT - 1) * partSize, contentLength,
                    mDownloadListener);
        } catch (Exception e) {
            e.printStackTrace();
            if (mDownloadListener != null) {
                mDownloadListener.onFailed();
            }
        }

    }


    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    protected abstract String getFilePath();
}
