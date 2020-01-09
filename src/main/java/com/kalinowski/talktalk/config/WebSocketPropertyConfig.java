package com.kalinowski.talktalk.config;

import com.kalinowski.talktalk.dao.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

@Configuration
@PropertySource("classpath:websocket.properties")
public class WebSocketPropertyConfig {

    @Value("${websocket.stomp.endpoint}")
    private String stompEndpoint;

    @Value("${websocket.mapping.prefix.message}")
    private String messageMappingPrefix;

    @Value("${websocket.mapping.prefix.destination}")
    private String destinationPrefix;

    @Value("${websocket.hostname}")
    private String hostName;

    @Value("${websocket.rabbitmq.port}")
    private String portNumber;

    @Value("${websocket.rabbitmq.user.login}")
    private String clientLogin;

    @Value("${websocket.rabbitmq.user.password}")
    private String clientPassword;

    @Bean
    public WebSocketProperties webSocketProperties() {
        WebSocketProperties webSocketProperties = new WebSocketProperties();
        webSocketProperties.setStompEndpoint(stompEndpoint);
        webSocketProperties.setMessageMappingPrefix(messageMappingPrefix);
        webSocketProperties.setDestinationPrefix(destinationPrefix);
        webSocketProperties.setHostName(hostName);
        webSocketProperties.setPortNumber(portNumber);
        webSocketProperties.setClientLogin(clientLogin);
        webSocketProperties.setClientPassword(clientPassword);
        return webSocketProperties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
