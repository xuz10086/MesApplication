package com.indusfo.spc.thread;

import java.util.HashMap;
import java.util.List;

public class ListThread {

    // 如果保存数据有多个,则使用Map集合
    private static ThreadLocal<HashMap<String, List>> listThread = new ThreadLocal<HashMap<String, List>>();

    public static void set(HashMap<String, List> list) {

        listThread.set(list);
    }

    public static HashMap<String, List> get() {

        return listThread.get();
    }

    // 线程销毁方法
    public static void remove() {

        listThread.remove();
    }
}
