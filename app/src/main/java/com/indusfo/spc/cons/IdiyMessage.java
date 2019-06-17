package com.indusfo.spc.cons;

/**
 * action标识类
 *  
 * @author xuz
 * @date 2019/1/11 9:21 AM
 * @param 
 * @return 
 */
public class IdiyMessage {

    // 登录action
    public static final int LOGIN_ACTION = 1;
    public static final int LOGIN_ACTION_RESULT = 2;

    // 查询批次表
    public static final int QUERY_BATCH = 3;
    public static final int QUERY_BATCH_RESULT = 4;
    public static final int QUERY_BATCH_NEXT_PAGE = 5;
    public static final int QUERY_BATCH_NEXT_PAGE_RESULT = 6;

    // 查询工艺目标
    public static final int QUERY_TARGET_BY_ID = 7;
    public static final int QUERY_TARGET_BY_ID_RESULT = 8;
    public static final int QUERY_TARGET = 9;
    public static final int QUERY_TARGET_RESULT = 10;

    // 查询检测条件值
    public static final int QUERY_DETE_VALUE = 11;
    public static final int QUERY_DETE_VALUE_RESULT = 12;

    // 保存检测条件值
    public static final int SAVE_DETE_VALUE = 13;
    public static final int SAVE_DETE_VALUE_RESULT = 14;

    // 查询外观检测项目
    public static final int QUERY_ASPECT_ITEM = 15;
    public static final int QUERY_ASPECT_ITEM_RESULT = 16;

    // 查询外观检测值
    public static final int QUERY_ASPECT_VALUE = 17;
    public static final int QUERY_ASPECT_VALUE_RESULT = 18;

    // 保存用户到数据库
    public static final int SAVE_USER_TODB = 19;
    public static final int SAVE_USER_TODB_RESULT = 20;
    public static final int GET_USER_FROM_DB = 21;
    public static final int GET_USER_FROM_DB_RESULT = 22;

    // 保存cookie到本地
    public static final int SAVE_COOKIE = 23;

    // 保存外观检测值
    public static final int SAVE_PRODUCT_ASPECT_DETE_VALUE = 24;
    public static final int SAVE_PRODUCT_ASPECT_DETE_VALUE_RESULT = 25;

    public static final int SET_TCP_MSG = 26;
    public static final int GET_TCP_MSG_RESULT = 27;

    // 查询设备的TCP连接信息
    public static final int GET_TCP_PARAMS = 28;
    public static final int GET_TCP_PARAMS_RESULT = 29;

    // 左右页面跳转
    public static final int RIGHT_VIEW_SHOW = 30;
    public static final int RIGHT_VIEW_SHOW_RESULT = 31;
    public static final int LEFT_SHOW_VIEW = 32;
    public static final int LEFT_SHOW_VIEW_RESULT = 33;

    // 设置url
    public static final int SETTING_URL = 34;

    public static final int GET_URL_FROM_LOCAL = 35;
    public static final int GET_URL_FROM_LOCAL_RESULT = 36;

    // 产品检测单判定
    public static final int SET_DETE_STATUE = 37;
    public static final int SET_DETE_STATUE_RESULT = 38;

    // 点击标题右侧保存，刷新检测值页面
    public static final int REFRESH_DETE_VALUE = 39;
    public static final int REFRESH_DETE_VALUE_RESULT = 40;
    public static final int SAVE_DETE_VALUE_AND_REFRESH = 41;
    public static final int SAVE_DETE_VALUE_AND_REFRESH_RESULT = 42;

    // 登陆用户对应工序查询
    public static final int QUERY_PRO_BY_LOGIN_USER = 43;
    public static final int QUERY_PRO_BY_LOGIN_USER_RESULT = 44;
}
