package com.kalinowski.talktalk.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String sender;
    private String recipient;
    private String content;
}
