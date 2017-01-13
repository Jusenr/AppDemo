package com.myself.appdemo.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.myself.appdemo.Constants;
import com.myself.appdemo.R;
import com.myself.appdemo.base.PTWDActivity;
import com.myself.appdemo.bean.FirInfoBean;
import com.myself.mylibrary.util.AppUtils;
import com.myself.mylibrary.util.DateUtils;
import com.myself.mylibrary.util.FileUtils;

import butterknife.BindView;

public class AppInfoActivity extends PTWDActivity {

    @BindView(R.id.tv_appname)
    TextView tv_appname;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_versionShort)
    TextView tv_versionShort;
    @BindView(R.id.tv_fsize)
    TextView tv_fsize;
    @BindView(R.id.tv_updatetime)
    TextView tv_updatetime;
    @BindView(R.id.tv_changelog)
    TextView tv_changelog;
    @BindView(R.id.tv_update_url)
    TextView tv_update_url;
    @BindView(R.id.tv_installUrl)
    TextView tv_installUrl;

    private FirInfoBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        String versionName = AppUtils.getVersionName(mContext);
        setRightTitle(versionName);
        mBean = (FirInfoBean) args.getSerializable(Constants.BundleKey.BUNDLE_APP_INFO);

        initView();
    }

    private void initView() {
        if (mBean != null) {
            tv_appname.setText(mBean.getName());
            tv_version.setText(mBean.getBuild());
            tv_versionShort.setText(mBean.getVersionShort());
            tv_fsize.setText(FileUtils.getFormatSize(mBean.getBinary().getFsize()));
            tv_updatetime.setText(DateUtils.millisecondToDate(mBean.getUpdated_at(), DateUtils.YMD_HMS_PATTERN));
            tv_changelog.setText(mBean.getChangelog());
            tv_update_url.setText(mBean.getUpdate_url());
            tv_installUrl.setText(mBean.getInstallUrl());
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
