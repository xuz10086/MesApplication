package com.indusfo.spc.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.indusfo.spc.bean.RResult;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.cons.NetworkConst;
import com.indusfo.spc.utils.NetworkUtil;

import java.util.HashMap;

public class ProductRightController extends BaseController {

    public ProductRightController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        RResult rResult;
        switch (action) {
            case IdiyMessage.QUERY_ASPECT_ITEM:
                rResult = queryAspectItem(url + NetworkConst.QUERY_ASPECT_ITEM_URL, (String) values[0]);
                mListener.onModeChange(IdiyMessage.QUERY_ASPECT_ITEM_RESULT, rResult);
                break;
            case IdiyMessage.QUERY_ASPECT_VALUE:
                rResult = queryAspectValue(url + NetworkConst.QUERY_ASPECT_VALUE_URL, (Integer) values[0]);
                mListener.onModeChange(IdiyMessage.QUERY_ASPECT_VALUE_RESULT, rResult);
                break;
            case IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE:
                rResult = saveProductAspectDeteValueResult(url + NetworkConst.SAVE_ASPECT_VALUE_URL, (String) values[0]);
                mListener.onModeChange(IdiyMessage.SAVE_PRODUCT_ASPECT_DETE_VALUE_RESULT, rResult);
                break;
            case IdiyMessage.LEFT_SHOW_VIEW:
                rResult = saveProductAspectDeteValueResult(url + NetworkConst.SAVE_ASPECT_VALUE_URL, (String) values[0]);
                mListener.onModeChange(IdiyMessage.LEFT_SHOW_VIEW_RESULT, rResult);
                break;
           default:
                break;
        }
    }

    /**
     * 保存外观检测值
     *
     * @author xuz
     * @date 2019/3/5 5:49 PM
     * @param [saveAspectValueUrl, value]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult saveProductAspectDeteValueResult(String url, String jsonList) {

        String json = NetworkUtil.doPostSetCookieJSON(url, jsonList, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 查询外观检测值
     *
     * @author xuz
     * @date 2019/2/28 5:30 PM
     * @param [url, lDeteId]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryAspectValue(String url, Integer lDeteId) {

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("lDeteId", lDeteId + "");
        String json = NetworkUtil.doPostSetCookie(url, params, cookie);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 查询外观检测项目
     *
     * @author xuz
     * @date 2019/2/28 5:30 PM
     * @param [url]
     * @return com.indusfo.spc.bean.RResult
     */
    private RResult queryAspectItem(String url, String lDeteId) {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("lInsType", "99");
        params.put("lDataState", "1");
        params.put("pagesize", "9999");
        params.put("pageindex", "1");
        params.put("lDeteId", lDeteId);
        String json = NetworkUtil.doPostSetCookie(url, params, cookie);
        return JSON.parseObject(json, RResult.class);

    }
}
