package com.hrconsultant.Fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;


public class TimePickerFragment extends DialogFragment{

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private int hour, minute;
    private boolean is24hrsFormat;



    public TimePickerFragment()
    {

    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener mOnTimeSetListener) {
        this.mOnTimeSetListener = mOnTimeSetListener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");
        is24hrsFormat= args.getBoolean("is24hrsFormat");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),mOnTimeSetListener, hour, minute,DateFormat.is24HourFormat(getActivity()));
      /*  return new TimePickerDialog(getActivity(),mOnTimeSetListener, hour, minute,
                false);*/
    }

}
