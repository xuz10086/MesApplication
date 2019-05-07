package com.indusfo.spc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.indusfo.spc.cons.AppParams;

/**
 * 数据库帮助类
 *  
 * @author xuz
 * @date 2019/1/11 9:21 AM
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    
    public DbOpenHelper(@Nullable Context context) {
        super(context, AppParams.DB_NAME, null, AppParams.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        db.execSQL("CREATE TABLE "+AppParams.USER_TABLE_NAME+"("+AppParams.USER_TABLE_NAME+" integer PRIMARY KEY AUTOINCREMENT, "+
                AppParams.USERNAME+" text, "+AppParams.PASSWORD+" text, "+AppParams.IFSAVE+" integer, "+AppParams.USERID+" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
