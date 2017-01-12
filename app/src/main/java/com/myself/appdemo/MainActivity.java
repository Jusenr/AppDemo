package com.myself.appdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myself.appdemo.bean.FirInfoBean;
import com.myself.appdemo.demo.AppInfoActivity;
import com.myself.appdemo.demo.TestActivity;
import com.myself.appdemo.qrcode.CaptureActivity;
import com.myself.mylibrary.controller.BasicFragmentActivity;
import com.myself.mylibrary.util.Logger;

import java.io.File;

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
//        startActivity(Main2Activity.class);

        //android 6.0+打开系统相机功能需要动态注册权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }

    public static String takePhoto(Context context, int requestCode) {
        String filePath = "";
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            filePath = TotalApplication.getInstance().getPackageCodePath() + File.separator + String.valueOf(System.currentTimeMillis()) + "camera" + ".png";
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
            ((Activity) context).startActivityForResult(intent, requestCode);

        }
        return filePath;
    }

    private void onMainClick() {
        startActivity(TestActivity.class);
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
