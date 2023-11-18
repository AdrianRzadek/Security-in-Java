package com.example.cyb1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserChangePasswordController {
     private static final Logger logger  = LoggerFactory.getLogger(UserChangePasswordController.class);
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/userChangePassword")
    public String userChangePassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        if (hasConsecutiveCharacters(newPassword)) {
            logger.info("Password should contain any sign only once!");
            return "Password should contain any sign only once!";
        }

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

    private boolean hasConsecutiveCharacters(String password) {
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                  logger.info("found consecutive characters");
                return true; // found consecutive characters
            }
        }
         logger.info("Not found consecutive characters");
        return false;
    }
}
