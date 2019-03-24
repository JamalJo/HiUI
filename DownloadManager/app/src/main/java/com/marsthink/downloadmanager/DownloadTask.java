package com.marsthink.downloadmanager;

import com.marsthink.downloadmanager.utils.MD5Util;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public class DownloadTask extends AbsDownloadTask {

    @DownLoadConstants.DownLoadState
    private int mDownloadState;

    public DownloadTask(String downloadUrl, DownloadListener downloadListener) {
        super();
        mDownloadListener = downloadListener;
        mDownloadUrl = downloadUrl;
        mDownloadState = DownLoadConstants.START;
    }

    public DownloadTask(String downloadUrl, DownloadListener downloadListener, long start,
            long end) {
        super();
        mDownloadListener = downloadListener;
        mDownloadUrl = downloadUrl;
        mDownloadState = DownLoadConstants.START;
        mStart = start;
        mEnd = end;
        isPartDownload = true;
    }

    public DownloadTask(String downloadUrl, @DownLoadConstants.DownLoadState int downloadState) {
        mDownloadUrl = downloadUrl;
        mDownloadState = downloadState;
    }

    @Override
    protected String getFilePath() {
        return DownLoadConstants.DOWNLOAD_PATH + MD5Util.md5(mDownloadUrl);
    }
}
