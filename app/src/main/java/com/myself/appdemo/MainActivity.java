package com.myself.appdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myself.appdemo.bean.FirInfoBean;
import com.myself.appdemo.demo.AppInfoActivity;
import com.myself.appdemo.demo.Main2Activity;
import com.myself.mylibrary.controller.BasicFragmentActivity;
import com.myself.mylibrary.util.Logger;

import butterknife.OnClick;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

public class MainActivity extends BasicFragmentActivity {

    private FirInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        getFirAppVersionInfo();
    }

    private void onLeftClick() {
        startActivity(Main2Activity.class);
    }

    private void onMainClick() {
        Toast.makeText(this, "onMainClick", Toast.LENGTH_SHORT).show();
    }

    private void onRightClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BundleKey.BUNDLE_APP_INFO, mBean);
        startActivity(AppInfoActivity.class, bundle);
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
     * Fir获取版本信息测试(FIR)
     */
    public void getFirAppVersionInfo() {
        FIR.checkForUpdateInFIR(TotalApplication.FIR_API_TOKEN, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.i("FIR", "check from fir.im success! " + "\n" + versionJson);
                mBean = new Gson().fromJson(versionJson, FirInfoBean.class);
                Logger.d("name------->" + mBean.getName() + "\n" +
                        "version----->" + mBean.getVersionShort() + "\n" +
                        "changelog--->" + mBean.getChangelog() + "\n" +
                        "installUrl-->" + mBean.getInstallUrl());
            }

            @Override
            public void onFail(Exception exception) {
                Log.i("FIR", "check fir.im fail! " + "\n" + exception.getMessage());
                Toast.makeText(getApplicationContext(), R.string.no_network_tips, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                if (null != mBean)
                    Toast.makeText(getApplicationContext(), "当前版本：" + mBean.getVersionShort(), Toast.LENGTH_SHORT).show();
            }
        });
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
