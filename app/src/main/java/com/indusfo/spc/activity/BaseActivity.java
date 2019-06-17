package com.indusfo.spc.activity;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.indusfo.spc.broad.NetWorkStateRecevier;
import com.indusfo.spc.controller.BaseController;
import com.indusfo.spc.listener.IModeChangeListener;

public abstract class BaseActivity extends AppCompatActivity implements IModeChangeListener {

    protected BaseController mController;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            handlerMessage(msg);
        }
    };
    private static Toast toast;

    NetWorkStateRecevier netWorkStateRecevier;

    protected void handlerMessage(Message msg) {
        // default Empty implementn
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    /**
     * 判断文本框输入的值是否为空
     *
     * @author xuz
     * @date 2019/1/4 9:17 AM
     * @param [values]
     * @return boolean
     */
    protected boolean ifValueWasEmpty(String... values) {

        for (String value : values) {
            if (TextUtils.isEmpty(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提示内容
     *
     * @author xuz
     * @date 2019/1/4 9:21 AM
     * @param [tipStr]
     * @return void
     */
    public void tip(String tipStr) {
//        Toast.makeText(this, tipStr, Toast.LENGTH_SHORT).show();
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), tipStr, Toast.LENGTH_SHORT);
        } else {
            toast.setText(tipStr);
        }
        toast.show();

    }

    @Override
    public void onModeChange(int action, Object... values) {
        mHandler.obtainMessage(action, values[0]).sendToTarget();
    }

    /**
     * 注册广播（Wi-Fi状态）
     *
     * @author xuz
     * @date 2019/6/17 11:08 AM
     * @param []
     * @return void
     */
    @Override
    protected void onResume() {
        if (null == netWorkStateRecevier) {
            netWorkStateRecevier = new NetWorkStateRecevier(this);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateRecevier, filter);
        super.onResume();
    }

    /**
     * 注销广播（Wi-Fi状态）
     *
     * @author xuz
     * @date 2019/6/17 11:08 AM
     * @param []
     * @return void
     */
    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateRecevier);
        super.onPause();
    }
}
