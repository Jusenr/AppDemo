package com.myself.mylibrary.view.picker;

import android.app.Activity;

import com.myself.mylibrary.view.picker.util.PickerDateUtils;

/**
 * 天数选择器
 * Created By guchenkai
 */
public class DayPicker extends OptionPicker {

    public DayPicker(Activity activity, int year, int month) {
        super(activity, new String[]{});
        //需要根据年份及月份动态计算天数
        int maxDays = PickerDateUtils.calculateDaysInMonth(year, month);
        for (int i = 1; i <= maxDays; i++) {
            options.add(i < 10 ? "0" + i : "" + i);
        }
    }
}