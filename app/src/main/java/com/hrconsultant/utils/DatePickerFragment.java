package com.hrconsultant.utils;

/**
 * Created by ManishJ1 on 6/29/2016.
 */
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {
    private OnDateSetListener mOnDateSetListener;
    private boolean maxDate;
    private int year, month, day;
    private boolean setMinimumDate;

    public DatePickerFragment() {
    }

    public void setCallBack(OnDateSetListener OnDateSetListener) {
        mOnDateSetListener = OnDateSetListener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
        setMinimumDate = true;
        maxDate = true;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), mOnDateSetListener, year, month, day);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.YEAR, 1920);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        if(setMinimumDate) {
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            Date date = calendar.getTime();
            dialog.getDatePicker().setMinDate(date.getTime());
        }
         if(maxDate){

            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            Date date = calendar.getTime();
            dialog.getDatePicker().setMaxDate(new Date().getTime());
        }

        return dialog;

    }
}
