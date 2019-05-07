package com.indusfo.spc.cons;

/**
 * App的常量类
 *
 * @author xuz
 * @date 2019/1/11 9:22 AM
 */
public class AppParams {

    // 数据库文件名
    public static final String DB_NAME = "contact4.db";
    // 数据库版本
    public static final int DB_VERSION = 3;
    // 表名及字段名
    public static final String USER_TABLE_NAME = "user";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String IFSAVE = "ifsave";
    public static final String USERID = "userId";

    // Cookie文件名
    public static final String COOKIE_FILE_DIR = "/data/data/com.indusfo.spc/cache";
    public static final String COOKIE_FILE_NAME = "cookie.txt";

    // URL文件名
    public static final String URL_FILE_DIR = "/data/data/com.indusfo.spc/cache";
    public static final String URL_FILE_NAME = "url.txt";

    public static final int PAGESIZE = 10;
    public static final int MAXPAGE = 5;

    // 称重仪器
    public static final String JJ_CODE = "1B 70";

    // app更新权限用户确认
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
}
