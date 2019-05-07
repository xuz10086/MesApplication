package com.indusfo.spc.utils;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 关闭流或者Socket
 *  
 * @author xuz
 * @date 2019/3/8 4:51 PM
 */
public class CloseUtil {

    public static final void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e("CloseErro", "...............................");
            }
        }
    }

    public static final void close(Socket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e("CloseErro", "Socket Close Erro...................");
            }
        }
    }

    public static final void close(ServerSocket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e("CloseErro", "...............................");
            }
        }
    }
}
