package com.kalinowski.talktalk.dao;

import lombok.*;

@Getter
@Setter
public class WebSocketProperties {

    private String stompEndpoint;
    private String messageMappingPrefix;
    private String destinationPrefix;
    private String hostName;
    private String portNumber;
    private String clientLogin;
    private String clientPassword;

}
