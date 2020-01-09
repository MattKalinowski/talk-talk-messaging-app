package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.auth.LoginUserDetails;
import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Middle layer for managing UserDetails objects
 *
 * Allows to return User Information regardless of persistence layer implementation.
 * In this case it uses JPA implementation.
 * */
@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return userOptional.map(LoginUserDetails::new).get();
    }
}
