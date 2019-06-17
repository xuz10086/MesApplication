package com.indusfo.spc.cons;

/**
 * URL常量类
 *  
 * @author xuz
 * @date 2019/1/11 9:21 AM
 */
public class NetworkConst {

    // 数据库存储数据状态，正常
    public static final String L_DATA_STATE = "1";

//    public static final String BASE_URL = "http://192.168.0.116:8080";
    public static final String BASE_URL = "";
//    public static final String BASE_URL = "http://10.0.0.28:8080";


    // 登陆
    public static final String LOGIN_URL = BASE_URL+"/doLogin";

    // 查询批次表
    public static final String QUERY_ALL_BATCH_URL = BASE_URL + "/QualityInspection/selectQualty";

    // 查询工艺目标
    public static final String QUERY_TARGET_URL = BASE_URL + "/detection/productDete/listProductDeteAssoDatas";
    public static final String QUERY_DETE_VALUE_URL = BASE_URL + "/detection/productDete/listProductDeteAssoDatas";

    // 保存检测值
    public static final String SAVE_DETE_VALUE_URL = BASE_URL + "/detection/productDeteValue/updateProductDeteValue";

    // 查询外观检测项目
    public static final String QUERY_ASPECT_ITEM_URL = BASE_URL + "/detePro/queryDetePro";

    // 查询外观检测值
    public static final String QUERY_ASPECT_VALUE_URL = BASE_URL + "/detection/productDeteAspect/queryProductDeteAspect";

    // 保存外观检测值
    public static final String SAVE_ASPECT_VALUE_URL = BASE_URL + "/detection/productDeteAspect/saveProductDeeAspect";

    // 获取天平的TCP连接信息
    public static final String GET_TCP_PARAMS_URL = BASE_URL + "/detection/Instrument/queryInstrument";

    // 产品检测单判定
    public static final String SET_DETE_RESULT_VALUE_URL = BASE_URL + "/detection/productDete/decide";

    // App在线升级
    public static final String UPDATA_VERSION_REQ = BASE_URL + "/pda/scanApp/queryScanApp";

    // 查询登陆用户对应工序
    public static final String QUERY_RRO_BY_LOGIN_USER = BASE_URL + "/role/selectRolePro";
}
