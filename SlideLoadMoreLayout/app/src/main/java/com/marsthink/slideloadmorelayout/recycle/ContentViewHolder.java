package com.marsthink.slideloadmorelayout.recycle;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marsthink.slideloadmorelayout.R;

/**
 * Created by zhoumao on 2019/3/19.
 * Description:
 */
public class ContentViewHolder extends ViewHolder {

    TextView mTextView;
    ImageView mImageView;

    public ContentViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
        mImageView = itemView.findViewById(R.id.img);
    }

    public void setData(String title, String content) {
        mTextView.setText(title);
        mImageView.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                R.drawable.dog));
    }
}
