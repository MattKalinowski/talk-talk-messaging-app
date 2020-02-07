package com.kalinowski.talktalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageHolder {
    private String senderName;
    private Integer conversationId;
    private String recipientsString;
    private String content;
}
