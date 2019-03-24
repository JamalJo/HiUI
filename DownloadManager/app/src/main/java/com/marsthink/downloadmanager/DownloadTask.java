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

    public DownloadTask(String downloadUrl, @DownLoadConstants.DownLoadState int downloadState) {
        mDownloadUrl = downloadUrl;
        mDownloadState = downloadState;
    }

    @Override
    protected String getFileName() {
        return MD5Util.md5(mDownloadUrl);
    }
}
