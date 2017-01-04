package com.myself.mylibrary.view.picker;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myself.mylibrary.view.picker.widget.PickerWheelView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 单项选择器
 *
 * @since 2015/9/29
 * Created By guchenkai
 */
public class OptionPicker extends WheelPicker {
    protected ArrayList<String> options = new ArrayList<String>();
    private OnOptionPickListener onOptionPickListener;
    private String selectedOption = "";
    private String label = "";
    private int selectedOptionIndex;
    private int selectedItem;

    public OptionPicker(Activity activity, String[] options) {
        super(activity);
        this.options.addAll(Arrays.asList(options));
    }

    public void setDefault(int defaultItem) {
        this.selectedItem = defaultItem;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOnOptionPickListener(OnOptionPickListener listener) {
        this.onOptionPickListener = listener;
    }

    @Override
    protected View initContentView() {
        if (options.size() == 0) {
            throw new IllegalArgumentException("please initial options at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        PickerWheelView optionView = new PickerWheelView(activity);
        optionView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        optionView.setTextSize(textSize);
        optionView.setTextColor(textColorNormal, textColorFocus);
        optionView.setLineVisible(lineVisible);
        optionView.setLineColor(lineColor);
        optionView.setOffset(offset);
        layout.addView(optionView);
        TextView labelView = new TextView(activity);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        labelView.setTextColor(textColorFocus);
        labelView.setTextSize(textSize);
        layout.addView(labelView);
        if (!TextUtils.isEmpty(label)) {
            labelView.setText(label);
        }
        optionView.setItems(options);
        optionView.setSelection(true, selectedItem - 1);
        optionView.setOnWheelViewListener(new PickerWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectedOptionIndex = selectedIndex;
                selectedOption = item;
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
                if (onOptionPickListener != null) {
                    selectedItem = selectedOptionIndex;
                    onOptionPickListener.onOptionPicked(selectedOption);
                }
            }
        });
    }

    public interface OnOptionPickListener {

        void onOptionPicked(String option);

    }

}
