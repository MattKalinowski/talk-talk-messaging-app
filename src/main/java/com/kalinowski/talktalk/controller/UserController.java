package com.kalinowski.talktalk.controller;

import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Set;

@Slf4j
@Controller("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/contacts")
    public Set<User> getUserContacts(Principal principal) {
        User user = userService.findUser(principal.getName());
        log.info("Returning contacts for user: {}", user.getUserName());
        return user.getContacts();
    }
}
