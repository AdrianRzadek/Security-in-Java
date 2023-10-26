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
public class DeleteUserController {

    @Autowired
    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userName") String userName) {

        if (userDetailsManager.userExists(userName)) {
            userDetailsManager.deleteUser(userName);
            return "User " + userName + " successfully deleted";
        }
        else {
            return "Username not found";
        }
    }
}