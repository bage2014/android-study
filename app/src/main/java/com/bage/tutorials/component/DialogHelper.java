package com.bage.tutorials.component;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogHelper {

    private Context context;
    private String positiveText = "OK";
    private String negativeText = "CANCEL";
    private int layoutResId = android.R.drawable.ic_menu_info_details;

    public DialogHelper(Context context) {
        this.context = context;
    }

    public AlertDialog showBasicDialog(String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .setIcon(android.R.drawable.ic_menu_info_details);
        return materialAlertDialogBuilder.show();
    }


    public AlertDialog showCustomDialog(int layoutResId, String title, @DrawableRes int iconId, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setView(layoutResId)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .setIcon(iconId);
        return materialAlertDialogBuilder.show();
    }

    public AlertDialog showCustomDialog(int layoutResId, String title, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setView(layoutResId)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .setIcon(this.layoutResId);
        return materialAlertDialogBuilder.show();
    }
}
