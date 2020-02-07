package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.model.User;

public interface UserService {
    User findUser(String username);
    void saveUser(User user);
    boolean isUserAlreadyPresent(User user);
}
