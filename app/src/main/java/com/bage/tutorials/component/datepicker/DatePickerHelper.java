package com.bage.tutorials.component.datepicker;

import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.bage.tutorials.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDate;
import java.util.Date;

public class DatePickerHelper {

    private Context context;

    public DatePickerHelper(Context context) {
        this.context = context;
    }


    public void show(FragmentManager manager, final DatePickerCallbackListener callbackListener) {

        long today = MaterialDatePicker.thisMonthInUtcMilliseconds();
//        Pair<Long, Long> todayPair = new Pair<>(today, today);
//        MaterialDatePicker.Builder<Pair<Long, Long>> builder =
//                MaterialDatePicker.Builder.dateRangePicker();
//        builder.setSelection(todayPair);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setSelection(today);

        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.materialCalendarTheme, typedValue, true)) {
            builder.setTheme(typedValue.data);
        }
        builder.setTitleText(R.string.date_picker_title_custom);

//            builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                callbackListener.onPositiveButtonClick(new Date(selection));
            }
        });
        picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                callbackListener.onCancel();
            }
        });
        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackListener.onNegativeButtonClick();
            }
        });
        picker.show(manager, picker.toString());
    }
}
