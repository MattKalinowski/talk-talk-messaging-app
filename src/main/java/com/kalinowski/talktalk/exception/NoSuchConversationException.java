package com.kalinowski.talktalk.exception;

public class NoSuchConversationException extends RuntimeException {
    public NoSuchConversationException(String message) {
        super(message);
    }
}
