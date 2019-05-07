package com.indusfo.spc.controller;

import android.content.Context;

import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.db.UserDb;
import com.indusfo.spc.listener.IModeChangeListener;
import com.indusfo.spc.utils.CookieUtil;
import com.indusfo.spc.utils.UrlUtils;

import java.io.File;

public abstract class BaseController {

    protected Context mContext;
    protected IModeChangeListener mListener;

    protected UserDb userDb;
    protected File cookieFile = new File(AppParams.COOKIE_FILE_DIR, AppParams.COOKIE_FILE_NAME);
    protected String cookie = CookieUtil.getCookie(cookieFile);
    protected File urlFile = new File(AppParams.URL_FILE_DIR, AppParams.URL_FILE_NAME);
    protected String url = UrlUtils.getURLFromLocal(urlFile);

    public BaseController(Context c) {
        mContext = c;
    }

    public void setIModeChangeListener(IModeChangeListener listener) {
        mListener = listener;
    }


    /**
     * 一个页面可能有多个请求
     *
     * @author xuz
     * @date 2019/1/4 9:24 AM
     * @param [action, values] [用来区分请求，请求的数据]
     * @return void
     */
    public void sendAsynMessage(final int action, final Object... values) {
        new Thread() {
            public void run() {
                handleMessage(action, values);
            }
        }.start();
    }

    /**
     * 子类处理请求的业务代码
     *
     * @author xuz
     * @date 2019/1/4 9:36 AM
     * @param [action, values]
     * @return void
     */
    protected abstract void handleMessage(int action, Object... values);
}
