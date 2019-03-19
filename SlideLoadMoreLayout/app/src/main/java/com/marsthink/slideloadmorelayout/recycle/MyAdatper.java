package com.marsthink.slideloadmorelayout.recycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marsthink.slideloadmorelayout.R;
import com.marsthink.slideloadmorelayout.recycle.ContentViewHolder;
import com.marsthink.slideloadmorelayout.recycle.HeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class MyAdatper extends RecyclerView.Adapter {

    private final static int TYPE_TITLE = 0;
    private final static int TYPE_CONTENT = 1;

    private List<String> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (getItemViewType(i)) {
            case TYPE_TITLE:
                TextView textView = new TextView(viewGroup.getContext());
                textView.setMaxEms(1);
                return new HeaderViewHolder(textView);
            case TYPE_CONTENT:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content,
                        null);
                return new ContentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).setData("header");
        } else if (viewHolder instanceof ContentViewHolder) {
            ((ContentViewHolder) viewHolder).setData(mList.get(i - 1), i + "");
        }
    }

    public void setData(List<String> list) {
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_CONTENT;
        }
    }
}
