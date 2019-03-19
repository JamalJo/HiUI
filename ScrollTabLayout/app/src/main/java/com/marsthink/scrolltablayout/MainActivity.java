package com.marsthink.scrolltablayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout=findViewById(R.id.tab_layout);
        mTabLayout.addTab("多发点");
        mTabLayout.addTab("的防守打法");
        mTabLayout.addTab("点");
        mTabLayout.addTab("发");
        mTabLayout.addTab("多");
        mTabLayout.addTab("发点");
        mTabLayout.addTab("咖啡");
        mTabLayout.addTab("艾弗森阿凡达");

    }
}
