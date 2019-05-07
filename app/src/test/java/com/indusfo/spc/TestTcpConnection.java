package com.indusfo.spc;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.indusfo.spc.activity.BaseActivity;
import com.indusfo.spc.cons.AppParams;
import com.indusfo.spc.cons.IdiyMessage;
import com.indusfo.spc.utils.TcpConnection;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestTcpConnection extends BaseActivity {

    //用于发送接收到的服务端的消息，显示在界面上
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.print(msg.obj.toString());
        }
    };

    @Test
    public void Test() {
        TcpConnection tcpConnection = new TcpConnection(handler,"192.168.0.203", 8000);
        new Thread(tcpConnection).start();

        // 提交数据
        Message message = new Message();
        message.what = IdiyMessage.SET_TCP_MSG;
        message.obj = AppParams.JJ_CODE;
        tcpConnection.revHandler.sendMessage(message);

    }

}
