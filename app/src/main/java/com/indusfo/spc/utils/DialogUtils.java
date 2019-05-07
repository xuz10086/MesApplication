package com.indusfo.spc.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.indusfo.spc.R;

public abstract class DialogUtils {
    public void showDialog(Context c, String title, String message, final String rightButton, final String leftButton) {
        // 弹框
        AlertDialog alertDialog = new AlertDialog.Builder(c)
                .setTitle(title).setMessage(message).setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(rightButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rightButtonMethod();
                    }
                })
                .setNegativeButton(leftButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        leftButtonMethod();
                    }
                }).create();
        alertDialog.show();
    }

    public abstract void leftButtonMethod();
    public abstract void rightButtonMethod();
}
