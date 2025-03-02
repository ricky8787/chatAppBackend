package com.example.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	 	@Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/ws")          // WebSocket連線端點
	                .setAllowedOriginPatterns("*")  // CORS (方便前端連線)
	                .withSockJS();                   // 支援SockJS
	    }

	    @Override
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.enableSimpleBroker("/topic");      // 訂閱端 (客戶端接收)
	        registry.setApplicationDestinationPrefixes("/app");  // 發送端 (客戶端發送)
	    }
}
