package com.seabed.sample.domain;

import com.seabed.sample.config.WebSocketEndpointConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: YoXung
 * @Date: 2019/12/26 1:03
 */
@Component
@ServerEndpoint(value = "/sampleWebSocket/{id}", configurator = WebSocketEndpointConfigure.class)
public class WebSocketService {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的ProductWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id")String id, Session session) {
        log.info("新客户端接入，用户id：" + session.getId());
        this.session = session;
        webSocketSet.add(this); // 加入set中
        onlineCount.incrementAndGet(); // 在线数自动加1

        if(id != null) {
            List<String> totalPushMsgs = new ArrayList<String>();
            totalPushMsgs.add(id + "连接成功-"+"-当前在线人数为：" + onlineCount.get());

            if(totalPushMsgs != null && !totalPushMsgs.isEmpty()) {
                totalPushMsgs.forEach(e -> sendMessage(e));
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("一个客户端关闭连接");
        webSocketSet.remove(this); // 从set中删除
        onlineCount.decrementAndGet();// 在线数减1
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户发送过来的消息为："+message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket出现错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            log.info("推送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketService webSocketService : webSocketSet) {
            webSocketService.sendMessage(message);
        }
    }

}
