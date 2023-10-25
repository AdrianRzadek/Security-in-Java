package com.example.cyb1;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final InMemoryUserDetailsManager userDetailsManager;

    public UserService(InMemoryUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }
}