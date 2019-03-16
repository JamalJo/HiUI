package com.marsthink.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;

    private int mNum;

    private RingProgressBar mRingProgressBar;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mNum++;
            mNum = mNum % 101;
            mRingProgressBar.setProgressNum(mNum);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRingProgressBar = findViewById(R.id.progress_bar);
        mRingProgressBar.setGraphMode(RingProgressBar.SECTOR);
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 100);
    }
}
