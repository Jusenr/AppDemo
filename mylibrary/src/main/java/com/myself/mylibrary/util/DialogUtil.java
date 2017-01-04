package com.myself.mylibrary.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by leo on 2016/12/15.
 */

public class DialogUtil {
    public static Dialog dialog;

    public static void show(Context context, String title, String message) {
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setMessage(message)
                .create();
        dialog.show();
    }

    public static void show(Context context, int resId) {
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder
                .setPositiveButton("确定", null)
                .setMessage(context.getResources().getString(resId))
                .create();
        dialog.show();
    }
}
