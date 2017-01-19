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
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myself.appdemo.bean.FirInfoBean;
import com.myself.appdemo.demo.AppInfoActivity;
import com.myself.appdemo.demo.TestActivity;
import com.myself.appdemo.playerdemo.IndexActivity;
import com.myself.appdemo.qrcode.CaptureActivity;
import com.myself.appdemo.video.MenuBean;
import com.myself.appdemo.video.YoukuVideoPlayerActivity;
import com.myself.mylibrary.controller.BasicFragmentActivity;
import com.myself.mylibrary.util.JsonUtils;
import com.myself.mylibrary.util.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

public class MainActivity extends BasicFragmentActivity {

    @BindView(R.id.btn_0)
    Button mBtn0;
    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.btn_2)
    Button mBtn2;
    @BindView(R.id.btn_3)
    Button mBtn3;

    private FirInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        getFirAppVersionInfo();
    }

    private void onLeftClick() {
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

    @OnClick({R.id.tv_left, R.id.tv_main, R.id.tv_right, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3})
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
            case R.id.btn_0:
                MenuBean menuBean = JsonUtils.parseData(MenuBean.menu_json, MenuBean.class);
                List<MenuBean.DataBean> data = menuBean.getData();
                Bundle bundle = new Bundle();
                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, data.get(0).getMenu_video());
                startActivity(YoukuVideoPlayerActivity.class, bundle);
                break;
            case R.id.btn_1:
                startActivity(IndexActivity.class);
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
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
