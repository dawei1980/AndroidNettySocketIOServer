package com.socketio.server.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketIOServer {

    private static com.corundumstudio.socketio.SocketIOServer server;

    public void startSocketio(String type, Object content){
        Configuration config = new Configuration();
//        config.setHostname("172.18.5.86");
        config.setHostname("192.168.1.104");
        config.setPort(8081);
        server = new com.corundumstudio.socketio.SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            // 添加客户端连接监听器
            public void onConnect(SocketIOClient client) {
                //logger.info(client.getRemoteAddress() + " web客户端接入");
                //不知道如何与客户端对应，好的办法是自己去写对应的函数
                client.sendEvent("connected", "hello");
            }
        });

        //监听客户端事件，client_info为事件名称，-自定义事件,从客户端获取数据
        server.addEventListener("client_info", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
                //客户端推送advert_info事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                System.out.println(clientIp + "：客户端：我已经连接服务器" + data);
            }
        });

        //添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取设备ip
                System.out.println(clientIp + "-------------------------" + "客户端已断开连接");
                //给客户端发送消息
                client.sendEvent("advert_info", clientIp + "客户端你好，我是服务端，期待下次和你见面");
            }
        });
        server.start();

        sendAllUser(type,content);
    }

    public void sendAllUser( String type, Object content ){
        while (true) {
            try {
                Thread.sleep(3000);
                //广播消息
                server.getBroadcastOperations().sendEvent(type, content);
                System.out.println(content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Title: startServer
     * @Description: 启动服务
     */
    public void startServer(String type, Object content) {
        if( server == null ){
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(() -> startSocketio(type,content));
        }
    }

    /**
     * @Title: stopSocketio
     * @Description: 停止服务
     */
    public void stopSocketio() {
        if( server != null ) {
            server.stop();
            server = null;
        }
    }
}
