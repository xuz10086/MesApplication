package com.indusfo.spc.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.indusfo.spc.R;
import com.indusfo.spc.activity.BaseActivity ;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 用于跳转页面
 *  
 * @author xuz
 * @date 2019/1/4 10:59 AM
 */
public class ActivityUtils {

    /**
     * 跳转
     *
     * @author xuz
     * @date 2019/1/4 10:59 AM
     * @param [c, clazz, ifFinishSelf]
     * @return void
     */
    public static void start(Context c, Class<? extends BaseActivity> clazz, boolean ifFinishSelf) {
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
        if (ifFinishSelf) {
            ((Activity) c).finish();
        }
    }

    public static void showDialog(Context c, String title, String message) {
        // 弹框
        AlertDialog alertDialog = new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(title).setMessage(message).create();
        alertDialog.show();
    }

    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


}

