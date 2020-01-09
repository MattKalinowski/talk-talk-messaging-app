package com.kalinowski.talktalk.model;

import lombok.*;

@Getter
@Setter
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
