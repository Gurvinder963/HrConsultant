package com.hrconsultant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends Activity{
    private LinearLayout llTop;
    private LinearLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        llTop =findViewById(R.id.llTop);

        root =findViewById(R.id.root);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                llTop.animate().translationY(-llTop.getHeight());

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                llTop.animate().translationY(0);

                return false;
            }
        });
    }
}
