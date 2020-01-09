package com.kalinowski.talktalk.config;

import com.kalinowski.talktalk.dao.*;
import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.*;

/**
 * Enables RabbitMQ message broker.
 * */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketProperties properties;

    public WebSocketConfig(WebSocketProperties properties) {
        this.properties = properties;
    }

    /**
     * Registers a WebSocket endpoint that the clients will use to connect to the WebSocket server.
     *
     * SockJS is used to enable fallback options for browsers that don’t support WebSocket.
     *
     * This method comes from Spring frameworks STOMP implementation.
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(properties.getStompEndpoint()).withSockJS();
    }

    /**
     * Configures a message broker that will be used to route messages from one client to another.
     *
     * The first line defines that all messages with MESSAGE_MAPPING_PREFIX prefix
     * will be routed to @MessageMapping-annotated methods in ChatController class.
     *
     * The second line defines that the messages whose destination starts with DESTINATION_PREFIX
     * should be routed to RabbitMQ message broker. RabbitMQ broadcasts messages to all the connected clients
     * who are subscribed to a particular topic.
     * */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(properties.getMessageMappingPrefix());

        registry.enableStompBrokerRelay(properties.getDestinationPrefix())
                .setRelayHost(properties.getHostName())
                .setRelayPort(Integer.parseInt(properties.getPortNumber()))
                .setClientLogin(properties.getClientLogin())
                .setClientPasscode(properties.getClientPassword());
    }
}
