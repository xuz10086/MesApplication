package com.indusfo.spc.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.cons.NetworkConst;
import com.indusfo.spc.utils.CookieUtil;
import com.indusfo.spc.utils.NetworkUtil;

import java.io.File;
import java.util.HashMap;

public class MainController extends BaseController {

    public MainController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        RResult rResult;
        switch (action) {
            case IdiyMessage.QUERY_BATCH:
                rResult = queryAllBatch(url + NetworkConst.QUERY_ALL_BATCH_URL, (JSONObject) values[0]);
                mListener.onModeChange(IdiyMessage.QUERY_BATCH_RESULT, rResult);
                break;
            case IdiyMessage.QUERY_BATCH_NEXT_PAGE:
                rResult = queryAllBatch(url + NetworkConst.QUERY_ALL_BATCH_URL, (JSONObject) values[0]);
                mListener.onModeChange(IdiyMessage.QUERY_BATCH_NEXT_PAGE_RESULT, rResult);
                break;
            case IdiyMessage.SET_DETE_STATUE:
                rResult = setDeteResultValue(url + NetworkConst.SET_DETE_RESULT_VALUE_URL, (String) values[0], (String) values[1]);
                mListener.onModeChange(IdiyMessage.SET_DETE_STATUE_RESULT, rResult);
                break;
            case IdiyMessage.QUERY_PRO_BY_LOGIN_USER:
                rResult = queryProByLoginUser(url+NetworkConst.QUERY_RRO_BY_LOGIN_USER);
                mListener.onModeChange(IdiyMessage.QUERY_PRO_BY_LOGIN_USER_RESULT, rResult);
                break;
            default:
                break;
        }
    }

    /**
     * 查询登陆用户对应工序
     *  
     * @author xuz
     * @date 2019/6/5 2:53 PM
     * @param [url]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryProByLoginUser(String url) {
        HashMap<String,String> hashMap = new HashMap<String, String>();
        String json = NetworkUtil.doPostSetCookie(url, hashMap, cookie);
        return JSON.parseObject(json, RResult.class);
    }


    /**
     * 产品检测单判定
     *
     * @author xuz
     * @date 2019/3/19 10:56 AM
     * @param [s, value, value1]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult setDeteResultValue(String url, String vcBatchCode, String lResult) {

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("vcBatchCode", vcBatchCode);
        hashMap.put("lResult", lResult);

        String json = NetworkUtil.doPostSetCookie(url, hashMap, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 查询所有批次
     *  
     * @author xuz
     * @date 2019/2/26 12:32 PM
     * @param [url]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryAllBatch(String url, JSONObject jsonObject) {

        String jsonString = jsonObject.toJSONString();
        String json = NetworkUtil.doPostSetCookieJSON(url, jsonString, cookie);
        return JSON.parseObject(json, RResult.class);
    }
}
