package com.example.cyb1.controller;

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
        .build();

        userDetailsManager.createUser(newUserDetails);
        
        if (userDetailsManager.userExists(newUser)) {
            return "New User Added!";
        }
        else {
            throw new UsernameNotFoundException("User not added :/");
        }
    }
}