package com.bage.tutorials.component;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

import com.bage.tutorials.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogHelper {

    private Context context;
    private String positiveText = "OK";
    private String negativeText = "CANCEL";
    private String infoTitle = "Info";
    private String confirmTitle = "Warning";
    private String errorTitle = "Ops";

    public DialogHelper(Context context) {
        this.context = context;
    }

    /////////////////////////////////////////// 基本弹框 ///////////////////////////////////////////
    public AlertDialog showBasicDialog(String message) {
        return showBasicDialog(infoTitle,message);
    }
    public AlertDialog showBasicDialog(String title, String message) {
        return showBasicDialog(title,message,null,null);
    }
    public AlertDialog showBasicDialog(String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
//                .setIconUrl(android.R.drawable.ic_dialog_info);
                .setIcon(android.R.drawable.ic_menu_info_details);
        return materialAlertDialogBuilder.show();
    }
    /////////////////////////////////////////// 基本弹框 ///////////////////////////////////////////

    /////////////////////////////////////////// 确认弹框 ///////////////////////////////////////////
    public AlertDialog showConfirmDialog(String message) {
        return showConfirmDialog(confirmTitle,message);
    }
    public AlertDialog showConfirmDialog(String title, String message) {
        return showConfirmDialog(title,message,null,null);
    }
    public AlertDialog showConfirmDialog(String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .setIcon(R.drawable.ic_dialog_alert_holo_light);
        return materialAlertDialogBuilder.show();
    }

    public AlertDialog showBasicErrorDialog(String message) {
        return showBasicErrorDialog(errorTitle,message);
    }
    public AlertDialog showBasicErrorDialog(String title, String message) {
        return showBasicErrorDialog(title,message,null,null);
    }
    public AlertDialog showBasicErrorDialog(String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
//                .setNegativeButton(negativeText, negativeListener)
                .setIcon(R.drawable.ic_dialog_indicator_input_error);
        return materialAlertDialogBuilder.show();
    }
    /////////////////////////////////////////// basic ///////////////////////////////////////////

    public AlertDialog showCustomDialog(int layoutResId, String title) {
        return showCustomDialog(layoutResId,title,android.R.drawable.ic_menu_info_details,null,null);
    }
    public AlertDialog showCustomDialog(int layoutResId, String title, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return showCustomDialog(layoutResId,title,android.R.drawable.ic_menu_info_details,positiveListener,negativeListener);
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

}
