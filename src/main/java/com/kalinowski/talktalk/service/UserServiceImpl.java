package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUser(String username) {
        Optional<User> userOptional = userRepository.findByUserName(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return userOptional.get();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        //TODO: Implement this method.
        return false;
    }

}
