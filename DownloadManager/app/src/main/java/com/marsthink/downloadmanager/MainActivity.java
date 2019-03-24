package com.marsthink.downloadmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zhoumao";

    private String[] urls = {
            "https://alissl.ucdl.pp.uc"
                    + ".cn/fs08/2019/03/21/6/110_25372b049559e5709bf9c6a9953ae1b2.apk",
            "http://img.hb.aicdn.com/bffd82d12213b6c626fc9d2c62b4405cdbd2bf3682fca-P07Wgi_fw658",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553366731786&di"
                    + "=370ec242b7c694084501fd4d6491bccd&imgtype=0&src=http%3A%2F%2Fimg1.3lian"
                    + ".com%2F2015%2Fa1%2F149%2Fd%2F129.jpg",
            "https://alissl.ucdl.pp.uc.cn/fs08/2019/03/20/6/2_d2807e6d3017563f890ee7a806919610.apk"
    };


    private ProgressBar mProgressBar;
    private Button mDownLoadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        mDownLoadBtn = findViewById(R.id.btn_download);

        mDownLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = urls[3];
                DownloadManager.getInstance().download(url, new DownloadListener() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "#######onStart: " + url);
                    }

                    @Override
                    public void onFailed() {
                        Log.d(TAG, "#######onFailed: " + url);
                    }

                    @Override
                    public void onRuning() {
                        Log.d(TAG, "#######onRuning: " + url);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "########onComplete: " + url);
                    }
                });
            }
        });
    }
}
