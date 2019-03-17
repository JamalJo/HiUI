package com.marsthink.downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> mDownloadList;

    ImageView mImageView;

    private static final String TAG = "zhoumao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        final Handler handler = new Handler();


        final DownLoadManager loadManager = new DownLoadManager();
        loadManager.setDownLoadListener(new DownLoadManager.DownLoadListener() {
            @Override
            public void onStart(String url) {
                Log.d(TAG, "onStart: " + url);
            }

            @Override
            public void onDownloaded(String url, String filePath) {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bitmap);
                        }
                    });
//                    if (bitmap != null) {
//                        bitmap.recycle();
//                    }
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                }
            }
        });

        initData();

        findViewById(R.id.download_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (String s : mDownloadList) {
                                loadManager.download(s);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void initData() {
        FileUtils.mkdirs(Constants.DOWNLOAD_PATH);
        mDownloadList = new ArrayList<>();
        mDownloadList.add("https://goss2.vcg.com/creative/vcg/800/new/VCG211191062387.jpg");
        mDownloadList.add("https://goss4.vcg.com/creative/vcg/800/version23/VCG41184301953.jpg");
        mDownloadList.add("https://goss3.vcg.com/creative/vcg/800/version23/VCG41184963242.jpg");

    }

    private void initView() {
        mImageView = findViewById(R.id.iv);
    }
}
