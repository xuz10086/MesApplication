package com.indusfo.spc.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.indusfo.spc.activity.LoginActivity;
import com.indusfo.spc.utils.NetworkUtil;
import com.indusfo.spc.utils.ToastUtils;

public class NetWorkStateRecevier extends BroadcastReceiver {
    private Context mContext;
    public NetWorkStateRecevier(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int i = NetworkUtil.checkedNetWorkType(context);
        if (i==NetworkUtil.NOWIFI) {
            ToastUtils.showToast(mContext, "Wi-Fi连接已断开");
        }
        if (i==NetworkUtil.NONETWORK) {
            ToastUtils.showToast(mContext, "网络未连接");
        }
    }
}
