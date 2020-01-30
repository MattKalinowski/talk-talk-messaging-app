package com.kalinowski.talktalk.config;

import com.kalinowski.talktalk.dao.WebSocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Enables RabbitMQ message broker.
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String STOMP_ENDPOINT = "/talk-talk";

    private final WebSocketProperties properties;

    public WebSocketConfig(WebSocketProperties properties) {
        this.properties = properties;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpointRegistry) {
        endpointRegistry.addEndpoint(STOMP_ENDPOINT).withSockJS();
        log.info("Registered {} endpoint", STOMP_ENDPOINT);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerConfig) {
        brokerConfig.setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/topic", "/queue", "/exchange")
                .setRelayHost(properties.getHostName())
                .setRelayPort(Integer.parseInt(properties.getPortNumber()))
                .setClientLogin(properties.getClientLogin())
                .setClientPasscode(properties.getClientPassword());
    }
}
