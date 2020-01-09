package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.model.User;

public interface UserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
}
