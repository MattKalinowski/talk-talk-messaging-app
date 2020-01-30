package com.kalinowski.talktalk.config;

import com.kalinowski.talktalk.dao.WebSocketProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:websocket.properties")
public class WebSocketPropertyConfig {

    @Value("${websocket.rabbitmq.hostname}")
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
