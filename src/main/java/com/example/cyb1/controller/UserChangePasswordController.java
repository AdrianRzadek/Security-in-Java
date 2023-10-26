package com.example.cyb1.controller;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

@PostMapping("/userChangePassword")
public String userChangePassword(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username = authentication.getName();
    // Pobierz UserDetails za pomocą UserDetailsService
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

 // For debugging - print the userDetails to console (remove this in production)
 System.out.println("Reloaded UserDetails: " + userDetails);

    if (passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        inMemoryUserDetailsManager.updatePassword(userDetails, passwordEncoder.encode(newPassword));
        return "Password changed successfully!";
    } else {
        return "Old password is incorrect!";
    }
}
}