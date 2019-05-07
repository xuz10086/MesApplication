package com.indusfo.spc.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indusfo.spc.R;

import java.util.Timer;
import java.util.TimerTask;

public class CustomDialog extends AlertDialog {
    private String str = "连接中";

    public CustomDialog(Context context) {
        super(context);
    }

    private TextView tv_loading;
    private ProgressBar progressBar;

    private Timer timer;
    private int count = 1;

    /**
     * 设置显示字幕
     *
     * @author xuz
     * @date 2019/3/9 1:50 PM
     * @param [_str]
     * @return void
     */
    public void setStr(String _str) {
        this.str = _str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        tv_loading = (TextView) findViewById(R.id.tv_loading);
        progressBar = (ProgressBar) findViewById(R.id.pb);

        // 设置Dialog显示的宽度，
        Display d = getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //这里设置为屏幕宽度的百分之八十
        lp.width = (int) (d.getWidth() * 0.8);
        getWindow().setAttributes(lp);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 300, 300);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (timer != null) {
                    timer.cancel();
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count++;
            if (count > 3) {
                count = 1;
            }
            switch (count) {
                case 1:
                    tv_loading.setText(str + ".");
                    break;
                case 2:
                    tv_loading.setText(str + "..");
                    break;
                case 3:
                    tv_loading.setText(str + "...");
                    break;
            }
        }
    };

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        if (progress == 100) {
            this.dismiss();
        }
    }
}
