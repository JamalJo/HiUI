package com.marsthink.slideloadmorelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marsthink.slideloadmorelayout.recycle.MyAdatper;
import com.marsthink.slideloadmorelayout.recycle.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    SlideLoadMoreLayout mSlideLoadMoreLayout;

    RecyclerView mRecyclerView;

    MyAdatper mMyAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideLoadMoreLayout = findViewById(R.id.layout_slide);
        mRecyclerView = mSlideLoadMoreLayout.getRecyclerView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mMyAdatper = new MyAdatper();
        mRecyclerView.setAdapter(mMyAdatper);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        initData();

        mSlideLoadMoreLayout.setOnSlidePullListener(new SlideLoadMoreLayout.OnSlidePullListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFingerReleased() {

            }
        });


    }

    private void initData() {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            list.add("item" + i);
        }
        mMyAdatper.setData(list);
        mMyAdatper.notifyDataSetChanged();
    }
}
