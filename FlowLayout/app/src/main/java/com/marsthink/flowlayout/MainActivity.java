package com.marsthink.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlowLayout = findViewById(R.id.flowlayout);
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            textView.setTextSize(20f);
            textView.setPadding(0, 5 * i, 0, 5 * i
            );
            textView.setText("  text" + i + "  ");
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mFlowLayout.addView(textView);
        }


    }
}
