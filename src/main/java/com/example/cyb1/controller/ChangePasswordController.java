package com.example.cyb1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ChangePasswordController {
     private static final Logger logger  = LoggerFactory.getLogger(ChangePasswordController.class);

    

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

@PostMapping("/changePassword")
public String changePassword(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("username") String username) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Pobierz UserDetails za pomocą UserDetailsService
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        inMemoryUserDetailsManager.updatePassword(userDetails, passwordEncoder.encode(newPassword));
        logger.info("Password changed successfully!");
        return "Password changed successfully!";
    } else {
         logger.info("Old password is incorrect!");
        return "Old password is incorrect!";
    }
}
}