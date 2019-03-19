package com.marsthink.slideloadmorelayout.recycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class HeaderViewHolder extends ViewHolder {

    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(String title){
        ((TextView)itemView).setText(title);
    }
}
