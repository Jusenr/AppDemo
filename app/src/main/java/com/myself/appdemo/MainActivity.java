package com.myself.appdemo;

import android.os.Bundle;

import com.myself.mylibrary.controller.BasicFragmentActivity;

public class MainActivity extends BasicFragmentActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
