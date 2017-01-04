package com.myself.mylibrary.view.picker;

import android.app.Activity;

/**
 * 分钟选择器
 * <p/>
 * Created By guchenkai
 */
public class MinutePicker extends OptionPicker {

    public MinutePicker(Activity activity) {
        super(activity, new String[]{});
        for (int i = 0; i < 60; i++) {
            options.add(i < 10 ? "0" + i : "" + i);
        }
    }
}