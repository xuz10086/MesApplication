package com.indusfo.spc.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.cons.NetworkConst;
import com.indusfo.spc.utils.NetworkUtil;

import java.util.HashMap;

public class ProductLeftController extends BaseController {

    public ProductLeftController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        RResult rResult;
        switch (action) {
            case IdiyMessage.QUERY_TARGET:
                rResult = queryTarget(url + NetworkConst.QUERY_TARGET_URL, (String) values[0]);
                mListener.onModeChange(IdiyMessage.QUERY_TARGET_RESULT, rResult);
                break;
            case IdiyMessage.QUERY_TARGET_BY_ID:
                rResult = queryDeteValue(url + NetworkConst.QUERY_TARGET_URL, (Integer) values[0], (String) values[1]);
                mListener.onModeChange(IdiyMessage.QUERY_TARGET_BY_ID_RESULT, rResult);
                break;
            case IdiyMessage.SAVE_DETE_VALUE:
                rResult = saveDeteValue(url + NetworkConst.SAVE_DETE_VALUE_URL, (Integer) values[0], (Integer) values[1], (String) values[2], (String) values[3]);
                mListener.onModeChange(IdiyMessage.SAVE_DETE_VALUE_RESULT, rResult);
                break;
            case IdiyMessage.GET_TCP_PARAMS:
                rResult = getTcpParams(url + NetworkConst.GET_TCP_PARAMS_URL, (JSONObject) values[0]);
                mListener.onModeChange(IdiyMessage.GET_TCP_PARAMS_RESULT, rResult);
                break;
            case IdiyMessage.RIGHT_VIEW_SHOW:
                rResult = saveDeteValue(url + NetworkConst.SAVE_DETE_VALUE_URL, (Integer) values[0], (Integer) values[1], (String) values[2], (String) values[3]);
                mListener.onModeChange(IdiyMessage.RIGHT_VIEW_SHOW_RESULT, rResult);
                break;
            case IdiyMessage.REFRESH_DETE_VALUE:
                rResult = queryDeteValue(url + NetworkConst.QUERY_TARGET_URL, (Integer) values[0], (String) values[1]);
                mListener.onModeChange(IdiyMessage.REFRESH_DETE_VALUE_RESULT, rResult);
                break;
            case IdiyMessage.SAVE_DETE_VALUE_AND_REFRESH:
                rResult = saveDeteValue(url + NetworkConst.SAVE_DETE_VALUE_URL, (Integer) values[0], (Integer) values[1], (String) values[2], (String) values[3]);
                mListener.onModeChange(IdiyMessage.SAVE_DETE_VALUE_AND_REFRESH_RESULT, rResult);
                break;
            default:
                break;
        }
    }

    /**
     * 获取检测仪器
     *
     * @author xuz
     * @date 2019/3/8 10:51 AM
     * @param [url, value, value1]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult getTcpParams(String url, JSONObject jsonObject) {
        String jsonStr = String.valueOf(jsonObject);
        String json = NetworkUtil.doPostSetCookieJSON(url, jsonStr, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 保存检测值
     *
     * @author xuz
     * @date 2019/2/28 10:13 AM
     * @param [url, value, timeValues, deteValues]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult saveDeteValue(String url,Integer lProTarget, Integer lDeteId, String timeValues, String deteValues) {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("lProTarget", lProTarget+"");
        params.put("lDeteId", lDeteId+"");
        params.put("vcValues", deteValues);
        params.put("dCreateTimes", timeValues);
        String json = NetworkUtil.doPostSetCookie(url, params, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 查询产品检测条件值
     *
     * @author xuz
     * @date 2019/2/27 2:55 PM
     * @param [queryDeteValueUrl, value]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryDeteValue(String url, Integer lProTarget, String vcBatchCode) {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("lProTarget", lProTarget+"");
        params.put("vcBatchCode", vcBatchCode);
        String json = NetworkUtil.doPostSetCookie(url, params, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 查询工艺目标
     *  
     * @author xuz
     * @date 2019/2/26 5:33 PM
     * @param [url, lProFlow]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryTarget(String url, String vcBatchCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("vcBatchCode", vcBatchCode);

        String json = NetworkUtil.doPostSetCookie(url, params, cookie);
        return JSON.parseObject(json, RResult.class);
    }
}
