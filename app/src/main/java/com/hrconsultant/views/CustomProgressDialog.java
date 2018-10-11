package com.hrconsultant.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.hrconsultant.R;


public class CustomProgressDialog extends ProgressDialog {

    private ProgressBar mLoadingView;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog(Context context, int theme, boolean isCancellable) {
        super(context, theme);
        setIndeterminate(true);
        setCancelable(isCancellable);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress_dialog);
        mLoadingView = (ProgressBar) findViewById(R.id.loadingView);
        mLoadingView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void show() {
        super.show();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoadingView.setVisibility(View.GONE);
    }

}
