package com.marsthink.slideloadmorelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.marsthink.slideloadmorelayout.recycle.MyAdatper;
import com.marsthink.slideloadmorelayout.recycle.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RefreshableView.PullToRefreshListener {

    private static final String TAG = "zhoumao";

    RefreshableView mRefreshableView;

    RecyclerView mRecyclerView;

    MyAdatper mMyAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRefreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        mRefreshableView.setOnRefreshListener(this, 0);

        mRecyclerView = findViewById(R.id.recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mMyAdatper = new MyAdatper();
        mRecyclerView.setAdapter(mMyAdatper);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            list.add("item"+i);
        }
        mMyAdatper.setData(list);
        mMyAdatper.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        mRefreshableView.finishRefreshing();
    }
}
