package com.marsthink.downloadmanager;

import com.marsthink.downloadmanager.utils.FileUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public class DownloadManager {

    private static volatile DownloadManager instance;

    private ExecutorService mExecutorService;

    private DownloadManager() {
        DownloadThreadFactory factory = new DownloadThreadFactory();
        FileUtils.mkdirs(DownLoadConstants.DOWNLOAD_PATH);
        mExecutorService = Executors.newFixedThreadPool(5, factory);
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    private void download(DownloadTask downloadTask) {
        mExecutorService.execute(downloadTask);
    }

    public void download(String downLoadUrl, DownloadListener downloadListener) {
        DownloadTask downloadTask = new DownloadTask(downLoadUrl, downloadListener);
        this.download(downloadTask);
    }

    public void download(String downLoadUrl, long start, long end,
            DownloadListener downloadListener) {
        DownloadTask downloadTask = new DownloadTask(downLoadUrl, downloadListener, start, end);
        this.download(downloadTask);
    }
}
