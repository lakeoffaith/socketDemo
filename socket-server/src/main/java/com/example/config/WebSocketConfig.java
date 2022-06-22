package com.example.config;

import com.example.handler.SimpleHandler;
import com.example.interceptor.SimpleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SimpleHandler simpleHandler;

    @Autowired
    private SimpleInterceptor simpleInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(simpleHandler,"/endpoint")
                .addInterceptors(simpleInterceptor)
                .setAllowedOrigins("*");
    }

}
