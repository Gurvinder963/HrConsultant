package com.hrconsultant.utils;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import java.util.List;

import com.hrconsultant.SendLocation;

/**
 * Created by ManishJ1 on 11/12/2016.
 */

public class TimeChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       Intent intent1 = new Intent(context, SendLocation.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
        } else {
            context.startService(intent1);
        }

    }


}
