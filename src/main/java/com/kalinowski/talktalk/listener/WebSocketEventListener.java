package com.kalinowski.talktalk.listener;

import com.kalinowski.talktalk.dto.MessageHolder;
import com.kalinowski.talktalk.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser().getName();
        log.info("Received a new web socket connection");
        log.info("User: {}", username);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) Objects.requireNonNull(
                headers.getSessionAttributes()).get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);

            MessageHolder message = new MessageHolder();
            message.setSenderName(username);

            messagingTemplate.convertAndSend("/topic/public", message);
        }
    }
}