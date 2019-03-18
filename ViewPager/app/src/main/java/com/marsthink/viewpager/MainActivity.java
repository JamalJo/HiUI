package com.marsthink.viewpager;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView aImg = new ImageView(this);
        aImg.setAdjustViewBounds(true);
        aImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.a));

        ImageView bImg = new ImageView(this);
        aImg.setAdjustViewBounds(true);
        bImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.b));

        ImageView cImg = new ImageView(this);
        aImg.setAdjustViewBounds(true);
        cImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.c));

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.addView(aImg);
        mViewPager.addView(bImg);
        mViewPager.addView(cImg);
    }
}
