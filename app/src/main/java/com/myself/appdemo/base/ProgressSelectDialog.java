package com.myself.appdemo.base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myself.appdemo.R;
import com.myself.mylibrary.controller.BasicDialog;
import com.myself.mylibrary.util.StringUtils;
import com.myself.mylibrary.view.RoundProgressBar;
import com.myself.mylibrary.view.image.ImageDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/11/10 11:48.
 */

public class ProgressSelectDialog extends BasicDialog implements View.OnClickListener {

    @BindView(R.id.progress)
    public RoundProgressBar progress;
    @BindView(R.id.tv_des)
    public TextView tv_des;
    @BindView(R.id.tv_content)
    public TextView tv_content;
    @BindView(R.id.view)
    public View view;
    @BindView(R.id.ll_tab)
    public LinearLayout ll_tab;
    @BindView(R.id.ll_first)
    LinearLayout ll_first;
    @BindView(R.id.iv_first)
    public ImageDraweeView iv_first;
    @BindView(R.id.tv_first)
    public TextView tv_first;
    @BindView(R.id.ll_second)
    public LinearLayout ll_second;
    @BindView(R.id.iv_second)
    public ImageDraweeView iv_second;
    @BindView(R.id.tv_second)
    public TextView tv_second;
    @BindView(R.id.tv_ok)
    public TextView tv_ok;

    private int progressMax = 100;
    public boolean tabVisibility = true;
    public boolean okVisibility = false;
    public boolean progressVisibility = false;
    public boolean isDisplayable = false;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_progress_confirmation;
    }

    public ProgressSelectDialog(Context context) {
        super(context);
        setOKVisibility(okVisibility);
//        setTabVisibility(tabVisibility);
        setProgressVisibility(progressVisibility);
    }

    public ProgressSelectDialog(Context context, boolean isOKVisibility) {
        super(context);
        setOKVisibility(isOKVisibility);
//        setTabVisibility(tabVisibility);
        setProgressVisibility(progressVisibility);
    }


    public ProgressSelectDialog(Context context, boolean isOKVisibility, boolean isProgressVisibility) {
        super(context);
//        setTabVisibility(tabVisibility);
        setOKVisibility(isOKVisibility);
        setProgressVisibility(isProgressVisibility);
    }

    public ProgressSelectDialog(Context context, String descText, int descTextColor, String firstText, int firstTextColor, int firstResId, String firstUrl, String secondText, int secondTextColor, String okText, int okTextColor, int max, float roundWidth, int cricleColor, int cricleProgressColor, int progressTextColor, int progressTextSize, int contentText, int contentTextColor, int secondResId, String secondUrl) {
        super(context);
        this.progressMax = max;
        tv_des.setText(descText);
        tv_des.setTextColor(descTextColor);
        tv_first.setText(firstText);
        tv_first.setTextColor(firstTextColor);
        iv_first.setImageResource(firstResId);
        iv_first.setImageURL(firstUrl);
        tv_second.setText(secondText);
        tv_second.setTextColor(secondTextColor);
        iv_second.setImageResource(secondResId);
        iv_second.setImageURL(secondUrl);
        tv_ok.setText(okText);
        tv_ok.setTextColor(okTextColor);
        tv_content.setText(contentText);
        tv_content.setTextColor(contentTextColor);
        progress.setMax(progressMax);
        progress.setTextSize(progressTextSize);
        progress.setTextColor(progressTextColor);
        progress.setCricleColor(cricleColor);
        progress.setCricleProgressColor(cricleProgressColor);
        progress.setRoundWidth(roundWidth);

        if (!StringUtils.isEmpty(firstUrl) || firstResId != -1)
            iv_first.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(secondUrl) || secondResId != -1)
            iv_second.setVisibility(View.VISIBLE);

//        setTabVisibility(tabVisibility);
        setOKVisibility(okVisibility);
        setProgressVisibility(progressVisibility);
    }

    public void setOKVisibility(boolean isOKVisibility) {
        tv_ok.setVisibility(isOKVisibility ? View.VISIBLE : View.GONE);
        ll_tab.setVisibility(isOKVisibility ? View.GONE : View.VISIBLE);
    }

    public void setProgressVisibility(boolean isOKVisibility) {
        progress.setVisibility(isOKVisibility ? View.VISIBLE : View.GONE);
    }

    public void setTabVisibility(boolean isTabVisibility) {
        ll_tab.setVisibility(isTabVisibility ? View.VISIBLE : View.GONE);
        tv_ok.setVisibility(isTabVisibility ? View.VISIBLE : View.GONE);
        view.setVisibility(isTabVisibility ? View.VISIBLE : View.GONE);
    }

    public void setDisplayable(boolean displayable) {
        isDisplayable = displayable;
    }

    @OnClick({R.id.ll_first, R.id.ll_second, R.id.tv_des, R.id.tv_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
                onLeftClick(v);
                break;
            case R.id.ll_second:
                onRightClick(v);
                break;
            case R.id.tv_des:
                onDescClick(v);
                break;
            case R.id.tv_ok:
                onOkClick(v);
                break;
        }
        dismiss();
    }

    /**
     * 点击ok
     *
     * @param v view
     */
    public void onOkClick(View v) {

    }

    /**
     * 点击left
     *
     * @param v view
     */
    public void onLeftClick(View v) {

    }

    /**
     * 点击right
     *
     * @param v view
     */
    public void onRightClick(View v) {

    }

    /**
     * 点击Desc
     *
     * @param v view
     */
    public void onDescClick(View v) {

    }

    /**
     * 设置进度
     *
     * @param gress
     */
    public void setProgress(int gress) {
        progress.setProgress(gress);
    }
}

