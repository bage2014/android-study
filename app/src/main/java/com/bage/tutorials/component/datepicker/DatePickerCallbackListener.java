package com.bage.tutorials.component.datepicker;

import java.util.Date;

public interface DatePickerCallbackListener {

    void onPositiveButtonClick(Date date);

    void onNegativeButtonClick();

    void onCancel();
}
