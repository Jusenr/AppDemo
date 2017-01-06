package com.myself.appdemo.demo;

import android.os.Bundle;

import com.myself.appdemo.R;
import com.myself.appdemo.base.PTWDActivity;

public class Main2Activity extends PTWDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
