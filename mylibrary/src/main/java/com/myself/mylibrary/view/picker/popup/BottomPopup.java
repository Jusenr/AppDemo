package com.myself.mylibrary.view.picker.popup;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.myself.mylibrary.util.DensityUtil;

/**
 * 底部弹窗基类
 *
 * @since 2015/7/19
 * Created by guchenkai
 */
public abstract class BottomPopup<V extends View> {
    public static final String TAG = BottomPopup.class.getSimpleName();
    protected static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    protected static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Activity activity;
    protected DensityUtil.Screen screen;
    private Popup popup;

    protected abstract V getView();

    public BottomPopup(Activity activity) {
        this.activity = activity;
        this.screen = DensityUtil.getScreenPixels(activity);
        Log.d(TAG, "screen width=" + screen.widthPixels + ", screen height=" + screen.heightPixels);
        popup = new Popup(activity);
        if (isFixedHeight()) {
            popup.setSize(screen.widthPixels, screen.heightPixels / 2);
        } else {
            popup.setSize(screen.widthPixels, WRAP_CONTENT);
        }
    }

    /**
     * 弹出窗显示之前调用
     */
    private void onShowPrepare() {
        setContentViewBefore();
        V view = getView();
        popup.setContentView(view);// 设置弹出窗体的布局
        setContentViewAfter(view);
    }

    /**
     * 是否固定高度为屏幕的一半
     */
    protected boolean isFixedHeight() {
        return false;
    }

    protected void setContentViewBefore() {
    }

    protected void setContentViewAfter(View contentView) {
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        popup.setOnDismissListener(onDismissListener);
        Log.d(TAG, "popup setOnDismissListener");
    }

    public void setSize(int width, int height) {
        popup.setSize(width, height);
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    public void show() {
        Log.d(TAG, "do something before popup show");
        onShowPrepare();
        popup.show();
        Log.d(TAG, "popup show");
    }

    public void dismiss() {
        popup.dismiss();
        Log.d(TAG, "popup dismiss");
    }
}
