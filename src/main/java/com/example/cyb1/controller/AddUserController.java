package com.example.cyb1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddUserController {
 private static final Logger logger  = LoggerFactory.getLogger(AddUserController.class);
    @Autowired
    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/addUser")
    public String addNewUser(@RequestParam("newUser") String newUser,
                             @RequestParam("newUserPassword") String newUserPassword) {
        System.out.println(newUser + " " + newUserPassword);
        
        // Create a new user details
        UserDetails newUserDetails = org.springframework.security.core.userdetails.User.builder()
        .username(newUser)
        .password(passwordEncoder.encode(newUserPassword))
        .roles("USER")
        .disabled(false)
        .build();

        userDetailsManager.createUser(newUserDetails);
        
        if (userDetailsManager.userExists(newUser)) {
            logger.info("New User Added!");
            return "New User Added!";
        }
        else {
            logger.info("User not added :/");
            throw new UsernameNotFoundException("User not added :/");
        }
    }
}