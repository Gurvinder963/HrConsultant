package com.hrconsultant;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hrconsultant.utils.GetLocation;

public class SendLocation extends IntentService{

    public SendLocation() {
        super("Some");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub



        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    public class MyThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub


            new GetLocation(SendLocation.this).build();




            stopSelf();
        }

    }
}
