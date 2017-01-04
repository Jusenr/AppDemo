package com.myself.mylibrary.view.picker;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.myself.mylibrary.view.picker.widget.PickerWheelView;

import java.util.List;

/**
 * Created by riven_chris on 16/7/14.
 */
public class MinuteStringPicker extends WheelPicker {

    private OnTimePickListener onTimePickListener;
    private String selectedMinute = "";
    private List<String> minutes;

    public MinuteStringPicker(Activity activity) {
        super(activity);
    }

    public void setOnTimePickListener(OnTimePickListener listener) {
        this.onTimePickListener = listener;
    }

    public void setMinutes(List<String> minutes) {
        this.minutes = minutes;
    }

    @Override
    protected View initContentView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        PickerWheelView hourView = new PickerWheelView(activity);
        hourView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        hourView.setTextSize(textSize);
        hourView.setTextColor(textColorNormal, textColorFocus);
        hourView.setLineVisible(lineVisible);
        hourView.setLineColor(lineColor);
        layout.addView(hourView);

        hourView.setItems(minutes);

        hourView.setOnWheelViewListener(new PickerWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectedMinute = item;
            }
        });
        return layout;
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        super.setContentViewAfter(contentView);
        super.setOnConfirmListener(new OnConfirmListener() {
            @Override
            public void onConfirm() {
                if (onTimePickListener != null) {
                    onTimePickListener.onTimePicked(selectedMinute);
                }
            }
        });
    }

    public interface OnTimePickListener {

        void onTimePicked(String minute);

    }
}
