package com.hrconsultant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.hrconsultant.R;


public class DialogFactory {
    public static DialogFactory dialogFactory;

    public static DialogFactory getInstance() {
        if (dialogFactory == null)
            dialogFactory = new DialogFactory();
        return dialogFactory;
    }

    /**
     * @param context                     Activity/Fragment context
     * @param dialogTitle                 Title of dialog
     * @param titleIcon                   Title Icon
     * @param dialogMessage               Message Body of Dialog
     * @param positiveButtonText          Positive Button Text
     * @param dialogPositiveClickListener Positive Button click listener
     * @param negativeButtonText          Negative button text
     * @param dialogNegativeClickListener Negative Button click listner
     * @param isCancellable               Can dialog be canceled by clicking on outside
     * @return Created dialog instance/ object
     */
    public AlertDialog showAlertDialog(Context context, @Nullable String dialogTitle, int titleIcon, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener dialogPositiveClickListener, @Nullable String negativeButtonText, @Nullable DialogInterface.OnClickListener dialogNegativeClickListener, boolean isCancellable) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setCancelable(isCancellable);
        builder.setInverseBackgroundForced(true);
        builder.setMessage(dialogMessage);
        if (titleIcon != 0)
            builder.setIcon(titleIcon);

        builder.setPositiveButton(positiveButtonText, dialogPositiveClickListener);

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, dialogNegativeClickListener);
        }

        if (dialogTitle != null) {
            builder.setTitle(dialogTitle);
        }

        alert = builder.create();
        alert.show();
        return alert;
    }

    public AlertDialog showAlertDialog(Context ctx, String dialogTitle, int titleIcon, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener dialogPositiveClickListener, String negativeButtonText, boolean isCancellable) {

        return showAlertDialog(ctx, dialogTitle, titleIcon, dialogMessage, positiveButtonText, dialogPositiveClickListener, negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }, isCancellable);

    }

    public AlertDialog showAlertDialog(Context ctx, String dialogTitle, int titleIcon, String dialogMessage, String positiveButtonText, boolean isCancellable) {
        return showAlertDialog(ctx, dialogTitle, titleIcon, dialogMessage, positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }, null, null, isCancellable);
    }


    public AlertDialog showAlertDialog(Context ctx, String dialogTitle, int titleIcon, String dialogMessage, String positiveButtonText, DialogInterface.OnClickListener onPositiveClickListener, boolean isCancellable) {
        return showAlertDialog(ctx, dialogTitle, titleIcon, dialogMessage, positiveButtonText, onPositiveClickListener, null, null, isCancellable);
    }



    public AlertDialog showCustomAlertDialog(Context context, int resLayoutId, View.OnClickListener clickListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(resLayoutId, null);
        dialogBuilder.setView(dialogView);
        /*For future find IDs and click listeners*/
        //View yesButton = dialogView.findViewById(R.id.yesButton);
        //View noButton = dialogView.findViewById(R.id.noButton);
        //yesButton.setOnClickListener(clickListener);
        //noButton.setOnClickListener(clickListener);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        return alertDialog;
    }

}
