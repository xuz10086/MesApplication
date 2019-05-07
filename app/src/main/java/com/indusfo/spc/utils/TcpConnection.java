package com.indusfo.spc.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Socket连接工具类
 *  
 * @author xuz
 * @date 2019/3/9 11:38 AM
 */
public class TcpConnection extends Thread {
    private String ipAddress;
    private int ipPort;
    private int action;

    private volatile static TcpConnection tcpConnection = null;

    // 用于向UI发送消息(接受到的信息)
    private Handler handler;
    // 接收UI线程的消息（要发送的信息）
    public Handler revHandler;

    public TcpConnection(Handler _handler, String address, int port, int _action) {
        this.handler = _handler;
        this.ipAddress = address;
        this.ipPort = port;
        this.action = _action;
    }

    Socket socket;
    InputStream is;
    BufferedReader br;
    OutputStream os;


    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(ipAddress, ipPort);
            socket.setSoTimeout(20000);
            if (socket.isConnected() && !socket.isClosed()) {
                is = socket.getInputStream();
//                Log.e("test", "connect success");
                // 接受服务器的消息
                br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                // 向服务器发送数据
                os = socket.getOutputStream();

                //读取数据会阻塞，所以创建一个线程来读取
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 接受服务器的消息，发送显示在界面
                        String content;
                        try {
                            while ((content=br.readLine()) != null) {
                                Message msg = new Message();
//                                msg.what = 0;
                                msg.what = action;
                                msg.obj = content;
                                handler.sendMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            // 非UI线程，自己创建x
            Looper.prepare();
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 将信息发送到服务器
                    try {
//                        os.write((msg.obj+"").getBytes("utf-8"));
                        byte[] bytes = StringUtils.hexStrToBinaryStr((String)msg.obj);

                        os.write(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Looper.loop();

        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("test", e.toString());
            // tcpResult.onFailed(e.toString());
            close();
        }
    }

    /**
     * 发生IO异常关闭流，外界调用close方法关闭流
     *  
     * @author xuz
     * @date 2019/3/9 11:36 AM
     * @param []
     * @return void
     */
    public void close() {
        Log.e("test", "连接断开");

        CloseUtil.close(socket);
        CloseUtil.close(br);
        CloseUtil.close(is);
    }
}

