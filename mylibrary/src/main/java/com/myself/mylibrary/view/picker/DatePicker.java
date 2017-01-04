package com.myself.mylibrary.view.picker;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myself.mylibrary.view.picker.util.PickerDateUtils;
import com.myself.mylibrary.view.picker.widget.PickerWheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 日期选择器
 *
 * @since 2015/12/14
 * Created By guchenkai
 */
public class DatePicker extends WheelPicker {
    private OnDatePickListener onDatePickListener;
    private String yearLabel = "年", monthLabel = "月", dayLabel = "日";
    private int startYear = 1970, endYear = 2050, startMonth = -1, startDay = -1, endMonth = -1, endDay = -1;
    private int selectedYear = 0, selectedMonth = 0, selectedDay = 0;
    private int defaultYear = 0, defaultMonth = 0, defaultDay = 0;
    private Mode mode = Mode.YEAR_MONTH_DAY;
    private boolean yearInit = true, monthInit = true, dayInit = true;

    public enum Mode {
        //年月日
        YEAR_MONTH_DAY,
        //年月
        YEAR_MONTH,
        //月日
        MONTH_DAY
    }

    public DatePicker(Activity activity) {
        this(activity, Mode.YEAR_MONTH_DAY);
    }

    public DatePicker(Activity activity, Mode mode) {
        super(activity);
        this.mode = mode;
        setDefault(System.currentTimeMillis());
    }

    public void setLabel(String yearLabel, String monthLabel, String dayLabel) {
        this.yearLabel = yearLabel;
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
    }

    public void setDefault(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        this.defaultYear = calendar.get(Calendar.YEAR);
        this.defaultMonth = calendar.get(Calendar.MONTH);
        this.defaultDay = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        selectedYear = defaultYear + 1;
        selectedMonth = defaultMonth + 1;
        selectedDay = defaultDay + 1;
        yearInit = true;
        monthInit = true;
        dayInit = true;
        if (defaultYear == startYear) {
            defaultMonth = defaultMonth - startMonth + 1;
            defaultDay = defaultDay - startDay + 1;
        }
    }

    /**
     * @see Mode#YEAR_MONTH_DAY
     * @see Mode#YEAR_MONTH
     */
    public void setRange(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public void setStartRange(int startYear, int startMonth, int startDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
    }

    public void setEndRange(int endYear, int endMonth, int endDay) {
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    @Override
    protected View initContentView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        PickerWheelView yearView = new PickerWheelView(activity);
        yearView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        yearView.setTextSize(textSize);
        yearView.setTextColor(textColorNormal, textColorFocus);
        yearView.setLineVisible(lineVisible);
        yearView.setLineColor(lineColor);
        yearView.setOffset(offset);
        layout.addView(yearView);
        TextView yearTextView = new TextView(activity);
        yearTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        yearTextView.setTextSize(textSize);
        yearTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(yearLabel)) {
            yearTextView.setText(yearLabel);
        }
        layout.addView(yearTextView);
        final PickerWheelView monthView = new PickerWheelView(activity);
        monthView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        monthView.setTextSize(textSize);
        monthView.setTextColor(textColorNormal, textColorFocus);
        monthView.setLineVisible(lineVisible);
        monthView.setLineColor(lineColor);
        monthView.setOffset(offset);
        layout.addView(monthView);
        final TextView monthTextView = new TextView(activity);
        monthTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        monthTextView.setTextSize(textSize);
        monthTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(monthLabel)) {
            monthTextView.setText(monthLabel);
        }
        layout.addView(monthTextView);
        final PickerWheelView dayView = new PickerWheelView(activity);
        dayView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dayView.setTextSize(textSize);
        dayView.setTextColor(textColorNormal, textColorFocus);
        dayView.setLineVisible(lineVisible);
        dayView.setLineColor(lineColor);
        dayView.setOffset(offset);
        layout.addView(dayView);
        TextView dayTextView = new TextView(activity);
        dayTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dayTextView.setTextSize(textSize);
        dayTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(dayLabel)) {
            dayTextView.setText(dayLabel);
        }
        layout.addView(dayTextView);
        if (mode.equals(Mode.YEAR_MONTH)) {
            dayView.setVisibility(View.GONE);
            dayTextView.setVisibility(View.GONE);
        } else if (mode.equals(Mode.MONTH_DAY)) {
            yearView.setVisibility(View.GONE);
            yearTextView.setVisibility(View.GONE);
        }

        if (!mode.equals(Mode.YEAR_MONTH)) {
            //年月日选择时，最大天数根据年月来计算
            int maxDays;
            int minDay = 1;
            if (mode.equals(Mode.YEAR_MONTH_DAY)) {
                maxDays = PickerDateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
            } else {
                maxDays = PickerDateUtils.calculateDaysInMonth(selectedMonth);
            }
            if (selectedYear == startYear && selectedMonth == startMonth && startDay > 0) {
                minDay = startDay;
            }
            ArrayList<String> days = new ArrayList<>();
            for (int i = minDay; i <= maxDays; i++) {
                days.add(PickerDateUtils.fillZore(i));
            }
            dayView.setItems(days);
            dayView.setSelection(false, defaultDay);
            dayView.setOnWheelViewListener(new PickerWheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    if (TextUtils.isEmpty(item)) {
                        return;
                    }
                    selectedDay = stringToYearMonthDay(item);
                }
            });
        }

        if (!mode.equals(Mode.MONTH_DAY)) {
            ArrayList<String> years = new ArrayList<>();
            for (int i = startYear; i <= endYear; i++) {
                years.add(String.valueOf(i));
            }
            int positionYear = defaultYear - startYear;
            yearView.setItems(years);
            yearView.setSelection(false, positionYear);
            yearView.setOnWheelViewListener(new PickerWheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    selectedYear = stringToYearMonthDay(item);
                    int minMonth = 1;
                    if (selectedYear == startYear && startMonth > 0) {
                        minMonth = startMonth;
                    }
                    if (selectedYear >= endYear) {//月份限制
                        if (endMonth > -1) {
                            final ArrayList<String> months = new ArrayList<>();
                            for (int i = minMonth; i <= endMonth; i++) {
                                months.add(PickerDateUtils.fillZore(i));
                            }
                            int position = selectedMonth > endMonth ? endMonth : selectedMonth;
                            monthView.setItems(months);
                            int defaultPosition = position;
                            if (startYear == selectedYear) {
                                defaultPosition = (position - startMonth + 1);
                            }
                            defaultPosition = defaultPosition > 0 ? defaultPosition : 1;
                            monthView.setSelection(false, defaultPosition - 1);
                        }
                    } else {
                        ArrayList<String> months = new ArrayList<>();
                        for (int i = minMonth; i <= 12; i++) {
                            months.add(PickerDateUtils.fillZore(i));
                        }
                        int position = selectedMonth > 0 ? selectedMonth : 1;
                        monthView.setItems(months);
                        int defaultPosition = position;
                        if (startYear == selectedYear) {
                            defaultPosition = position - startMonth + 1;
                        }
                        defaultPosition = defaultPosition > 0 ? defaultPosition : 1;
                        monthView.setSelection(false, defaultPosition - 1);
                    }

                    //需要根据年份及月份动态计算天数
                    int maxDays = PickerDateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
                    if (selectedYear >= endYear && endMonth > -1 &&
                            selectedMonth >= endMonth && maxDays > endDay) {
                        maxDays = endDay;
                    }
                    int minDay = 1;
                    if (selectedYear == startYear && selectedMonth == startMonth && startDay > 0) {
                        minDay = startDay;
                    }
                    ArrayList<String> days = new ArrayList<>();
                    for (int i = minDay; i <= maxDays; i++) {
                        days.add(PickerDateUtils.fillZore(i));
                    }
                    if (yearInit) {
                        yearInit = false;
                    } else {
                        if (dayInit) {
                            initDays(dayView, days);
                        } else {
                            resetDays(dayView, days, maxDays);
                        }
                    }
                }
            });
        }


        ArrayList<String> months = new ArrayList<>();
        int minMonth = 1;
        if (startMonth > 0)
            minMonth = startMonth;
        for (int i = minMonth; i <= 12; i++) {
            months.add(PickerDateUtils.fillZore(i));
        }
        monthView.setItems(months);
        monthView.setSelection(false, defaultMonth);
        monthView.setOnWheelViewListener(new PickerWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectedMonth = stringToYearMonthDay(item);
                //需要根据年份及月份动态计算天数
                int maxDays = PickerDateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
                if (selectedYear >= endYear && endMonth > -1 &&
                        selectedMonth >= endMonth && maxDays > endDay) {
                    maxDays = endDay;
                }
                int minDay = 1;
                if (selectedYear == startYear && selectedMonth == startMonth && startDay > 0) {
                    minDay = startDay;
                }
                ArrayList<String> days = new ArrayList<>();
                for (int i = minDay; i <= maxDays; i++) {
                    days.add(PickerDateUtils.fillZore(i));
                }
                if (monthInit) {
                    monthInit = false;
                } else {
                    if (dayInit) {
                        initDays(dayView, days);
                    } else {
                        resetDays(dayView, days, maxDays);
                    }
                }
            }
        });

        return layout;
    }

    private int stringToYearMonthDay(String text) {
        if (text.startsWith("0") && text.length() >= 2)
            //截取掉前缀0以便转换为整数
            text = text.substring(1);
        return Integer.parseInt(text);
    }

    private void resetDays(PickerWheelView dayView, ArrayList<String> days, int maxDays) {
//        if (dayView.getItemsCount() != days.size()) {
        final int positionDay = Math.min(selectedDay - 1, maxDays - 1);
        int defaultPosition = positionDay;
        if (startYear == selectedYear) {
            defaultPosition = positionDay - startDay;
            defaultPosition = defaultPosition > 0 ? defaultPosition : 0;
        }
        dayView.setItems(days);
        dayView.setSelection(false, defaultPosition);
//        }
    }

    private void initDays(PickerWheelView dayView, ArrayList<String> days) {
        dayView.setItems(days);
        dayView.setSelection(false, defaultDay);
        dayInit = false;
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        super.setContentViewAfter(contentView);
        super.setOnConfirmListener(new OnConfirmListener() {
            @Override
            public void onConfirm() {
                if (onDatePickListener != null) {
                    String day = PickerDateUtils.fillZore(selectedDay);
                    String month = PickerDateUtils.fillZore(selectedMonth);
                    String year = String.valueOf(selectedYear);
                    switch (mode) {
                        case YEAR_MONTH:
                            ((OnYearMonthPickListener) onDatePickListener).onDatePicked(year, month);
                            break;
                        case MONTH_DAY:
                            ((OnMonthDayPickListener) onDatePickListener).onDatePicked(month, day);
                            break;
                        default:
                            ((OnYearMonthDayPickListener) onDatePickListener).onDatePicked(year, month, day);
                            break;
                    }
                }
            }
        });
    }

    protected interface OnDatePickListener {

    }

    public interface OnYearMonthDayPickListener extends OnDatePickListener {

        void onDatePicked(String year, String month, String day);
    }

    public interface OnYearMonthPickListener extends OnDatePickListener {

        void onDatePicked(String year, String month);
    }

    public interface OnMonthDayPickListener extends OnDatePickListener {

        void onDatePicked(String month, String day);
    }
}
