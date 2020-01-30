package com.kalinowski.talktalk.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketProperties {
    private String hostName;
    private String portNumber;
    private String clientLogin;
    private String clientPassword;
}
