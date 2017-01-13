package com.myself.appdemo.demo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.myself.appdemo.R;
import com.myself.appdemo.base.PTWDActivity;
import com.myself.mylibrary.util.FileUtils;
import com.myself.mylibrary.util.SDCardUtils;
import com.myself.mylibrary.util.ToastUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends PTWDActivity {

    @BindView(R.id.tv_path)
    TextView mTvPath;
    @BindView(R.id.tv_tag)
    TextView mTvTag;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;

    private String mSdCardPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSdCardPath = SDCardUtils.getSDCardPath() + File.separator + "app_demo";

        File file = new File(mSdCardPath);
        boolean exists = file.exists();
        ToastUtils.showToastShort(this, "exists-->" + exists);
        if (!exists)
            file.mkdirs();
        String path = file.getAbsolutePath();

        mTvPath.setText(mSdCardPath);
        mTvTag.setText(path);
        mBtnCommit.setText("init");
    }

    private void initData() {
        try {
            File setFile = new File(mSdCardPath + File.separator + "patch/biaoqing/set.txt");
            File setFile2 = new File(mSdCardPath + File.separator + "patch/biaoqing/001.png");
            if (!setFile.exists() || !setFile2.exists())
                FileUtils.unZipInAsset(getApplicationContext(), "patch_10002_10003.zip", "patch", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_commit)
    public void onClick() {
        initData();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
