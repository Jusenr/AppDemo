package com.myself.mylibrary.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myself.mylibrary.util.NetworkLogUtil;

/**
 * Created by Administrator on 2016/4/7.
 */
public class NetworkLogActivty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(params);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(params);

        TextView textView = new TextView(this);
        textView.setLayoutParams(params);

        frameLayout.addView(textView);
        scrollView.addView(frameLayout);

        setContentView(scrollView);

        textView.setText(NetworkLogUtil.networkLog.toString());
    }
}
