package com.marsthink.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zhoumao on 2019/3/17.
 * Description:
 */
public class DownLoadManager {

    private DownLoadListener mDownLoadListener;

    public DownLoadManager() {
    }

    public void download(String downloadUrl) throws IOException {
        if (mDownLoadListener != null) {
            mDownLoadListener.onStart(downloadUrl);
        }
        URL url = new URL(downloadUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();

        String filePath = Constants.DOWNLOAD_PATH + System.currentTimeMillis() + ".jpg";
        OutputStream os = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        while (len > 0) {
            os.write(buffer);
            len = is.read(buffer);
        }
        if (mDownLoadListener != null) {
            mDownLoadListener.onDownloaded(downloadUrl, filePath);
        }
        os.flush();
        is.close();
        os.close();
    }

    public void setDownLoadListener(
            DownLoadListener downLoadListener) {
        mDownLoadListener = downLoadListener;
    }

    public interface DownLoadListener {
        void onStart(String url);

        void onDownloaded(String url, String filePath);
    }

}
