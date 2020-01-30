package com.kalinowski.talktalk.controller;

import com.kalinowski.talktalk.model.Message;
import com.kalinowski.talktalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    /**
     * Receives message from STOMP Client and persist it to a database.
     * MessageMapping annotation reflects destination of SEND frame.
     * SendToUser annotation implies that return value will be sent back to a sender.
     * SimpMessagingTemplate is used to send the message to a recipient.
     */
    @MessageMapping("/chat")
    @SendToUser("/queue/private-chat")
    public Message processPrivateMessage(@Payload Message message, Principal sender) {
        String recipient = message.getRecipient();
        String destination = "/queue/private-chat";

        simpMessagingTemplate.convertAndSendToUser(recipient, destination, message);

        log.info("[sendPrivateMessage] sender:{}, recipient: {}, messageContent: {}",
                sender.getName(), recipient, message.getContent());

        return message;
    }

}
