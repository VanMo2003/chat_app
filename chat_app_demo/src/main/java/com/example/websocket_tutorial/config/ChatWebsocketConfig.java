package com.example.websocket_tutorial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class ChatWebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // Định nghĩa tiền tố khi gửi
        registry.enableSimpleBroker("/chatroom", "/user"); // Cả topic (chung) và queue (riêng biệt)
        registry.setUserDestinationPrefix("/user"); // Định nghĩa tiền tố khi gửi
    }
}
