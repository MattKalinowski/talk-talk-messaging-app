package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.model.User;

import java.util.Set;

public interface UserService {
    User findUser(String username);
    void saveUser(User user);
    boolean isUserAlreadyPresent(User user);
    Set<User> getUserContacts(User user);
}
