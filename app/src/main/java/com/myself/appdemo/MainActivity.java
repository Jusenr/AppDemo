package com.myself.appdemo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.myself.mylibrary.controller.BasicFragmentActivity;

import butterknife.OnClick;

public class MainActivity extends BasicFragmentActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    private void onLeftClick() {
    }

    private void onMainClick() {
    }

    private void onRightClick() {
    }

    @OnClick({R.id.tv_left, R.id.tv_main, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                onLeftClick();
                break;
            case R.id.tv_main:
                onMainClick();
                break;
            case R.id.tv_right:
                onRightClick();
                break;
        }
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}
