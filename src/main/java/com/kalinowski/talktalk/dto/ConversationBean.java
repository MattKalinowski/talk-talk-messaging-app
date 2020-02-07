package com.kalinowski.talktalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationBean {
    private String id;
    private String name;
    private String pictureURL;
    private String lastMessage;
    private String lastMessageTime;
    private String status;
}
