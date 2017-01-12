package com.myself.appdemo.qrcode;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.myself.appdemo.MainActivity;
import com.myself.appdemo.R;
import com.myself.appdemo.api.ScanApi;
import com.myself.appdemo.base.PTWDActivity;
import com.myself.mylibrary.controller.ActivityManager;
import com.myself.mylibrary.http.callback.JSONObjectCallback;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网页版登录确认
 * Created by guchenkai on 2015/12/9.
 */
public class WebLoginActivity extends PTWDActivity implements View.OnClickListener {
    public static final String URL_LOGIN = "url_login";

    @BindView(R.id.ll_qr_code_toast)
    LinearLayout ll_qr_code_toast;//二维码toast

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        url = args.getString(URL_LOGIN);
        Logger.d("url:" + url);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_login, R.id.tv_cancel_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                networkRequest(ScanApi.confirmLogin(url), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        int error_code = result.getInteger("error_code");
                        if (error_code == 0)
                            ToastUtils.showToastLong(mContext, "葡萄官网登录成功");
                        else {
                            String error = result.getString("error");
                            ToastUtils.showToastLong(mContext, TextUtils.isEmpty(error) ? "登录失败" : error);
                        }

                        loading.dismiss();
//                        finish();
                        ActivityManager.getInstance().popOtherActivity(MainActivity.class);
                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                    }
                });
                break;
            case R.id.tv_cancel_login://取消登录
                ActivityManager.getInstance().popOtherActivity(MainActivity.class);
                break;
        }
    }
}
