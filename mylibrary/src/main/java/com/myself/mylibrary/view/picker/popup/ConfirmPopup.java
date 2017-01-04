package com.myself.mylibrary.view.picker.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 带确定及取消按钮的
 *
 * @since 2015/10/21
 * Created By guchenkai
 */
public abstract class ConfirmPopup<V extends View> extends BottomPopup<View> implements View.OnClickListener {
    public static final String TAG = ConfirmPopup.class.getSimpleName();
    protected static final String TAG_SUBMIT = "submit";
    protected static final String TAG_CANCEL = "cancel";
    private boolean topLineVisible = true;
    private int topLineColor = 0xFFDDDDDD;
    private boolean cancelVisible = true;
    private CharSequence cancelText = "", submitText = "";
    private int cancelTextColor = Color.BLACK;
    private int submitTextColor = Color.BLACK;
    private OnConfirmListener onConfirmListener;
    private TextView tvTitle;

    public void setTitleText(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setTitleTextSize(int px) {
        if (tvTitle != null) {
            tvTitle.setTextSize(px);
        }
    }

    public void setTitleTextColorRes(int resColor) {
        if (tvTitle != null) {
            tvTitle.setBackgroundResource(resColor);
        }
    }

    public String getTitleText() {
        if (tvTitle != null) {
            return tvTitle.getText().toString();
        } else {
            return null;
        }

    }

    public ConfirmPopup(Activity activity) {
        super(activity);
        cancelText = activity.getString(android.R.string.cancel);
        submitText = activity.getString(android.R.string.ok);
    }

    protected abstract V initContentView();

    public void setTopLineColor(int topLineColor) {
        this.topLineColor = topLineColor;
    }

    public void setTopLineVisible(boolean topLineVisible) {
        this.topLineVisible = topLineVisible;
    }

    public void setCancelVisible(boolean cancelVisible) {
        this.cancelVisible = cancelVisible;
    }

    public void setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText;
    }

    public void setSubmitText(CharSequence submitText) {
        this.submitText = submitText;
    }

    public void setCancelTextColor(@ColorInt int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }

    public void setSubmitTextColor(@ColorInt int submitTextColor) {
        this.submitTextColor = submitTextColor;
    }

    @Override
    protected View getView() {
        LinearLayout rootLayout = new LinearLayout(activity);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setClipToPadding(false);
        RelativeLayout topButtonLayout = new RelativeLayout(activity);
        topButtonLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, dp2px(activity, 40)));
        topButtonLayout.setBackgroundColor(Color.WHITE);
        topButtonLayout.setGravity(Gravity.CENTER_VERTICAL);
        Button cancelButton = new Button(activity);
        cancelButton.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        cancelButton.setTag(TAG_CANCEL);
        RelativeLayout.LayoutParams cancelButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        cancelButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        cancelButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        cancelButton.setLayoutParams(cancelButtonLayoutParams);
        cancelButton.setBackgroundColor(Color.TRANSPARENT);
        cancelButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(cancelText)) {
            cancelButton.setText(cancelText);
        }
        cancelButton.setTextColor(cancelTextColor);
        cancelButton.setOnClickListener(this);
        topButtonLayout.addView(cancelButton);
        Button submitButton = new Button(activity);
        submitButton.setTag(TAG_SUBMIT);
        RelativeLayout.LayoutParams submitButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        submitButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        submitButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        submitButton.setLayoutParams(submitButtonLayoutParams);
        submitButton.setBackgroundColor(Color.TRANSPARENT);
        submitButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(submitText)) {
            submitButton.setText(submitText);
        }
        submitButton.setTextColor(submitTextColor);
        submitButton.setOnClickListener(this);
        topButtonLayout.addView(submitButton);
        tvTitle = new Button(activity);
        tvTitle.setBackgroundColor(Color.TRANSPARENT);
        RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        titleParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topButtonLayout.addView(tvTitle, titleParam);
        rootLayout.addView(topButtonLayout);
        if (topLineVisible) {
            View lineView = new View(activity);
            lineView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, 1));
            lineView.setBackgroundColor(topLineColor);
            rootLayout.addView(lineView);
        }
        rootLayout.addView(initContentView());
        return rootLayout;
    }

    @Override
    public void onClick(View v) {
        if (onConfirmListener != null) {
            String tag = v.getTag().toString();
            if (tag.equals(TAG_SUBMIT)) {
                onConfirmListener.onConfirm();
            } else {
                onConfirmListener.onCancel();
            }
        }
        dismiss();
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public static abstract class OnConfirmListener {

        public abstract void onConfirm();

        public void onCancel() {

        }

    }

    private int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}
