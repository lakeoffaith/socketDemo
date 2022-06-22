package com.example.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Component
public class SocketClientConfig {
    Logger logger=Logger.getLogger("com.example.config.SocketClientConfig");
    private StandardWebSocketClient standardWebSocketClient;
    private WebSocketSession webSocketSession;

    @PostConstruct
    public void connect() throws ExecutionException, InterruptedException {
         standardWebSocketClient = new StandardWebSocketClient();
        webSocketSession = standardWebSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("【客户端】建立连接成功，sessionId:"+session.getId());
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                System.out.println("【客户端】接收到消息，内容:"+message.getPayload());
            }
        }, new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8000/endpoint")).get();


        //发送消息
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(new Date());
        try {
            webSocketSession.sendMessage(new TextMessage("i'm client,sendTime:" + format));
        } catch (IOException e) {
            logger.severe("client send message fail,please make sure the server work well");
        }
        logger.info("well down!");
    }
}
