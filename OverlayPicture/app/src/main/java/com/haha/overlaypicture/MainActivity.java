package com.haha.overlaypicture;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zhoumao";

    OverlayPictureView mOverlayPictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOverlayPictureView = findViewById(R.id.overlay_view);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 2);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: "+animation.getAnimatedValue());
                mOverlayPictureView.setRadio((float) (animation.getAnimatedValue()));
            }
        });
        animator.setRepeatCount(2);
        animator.start();
    }
}
