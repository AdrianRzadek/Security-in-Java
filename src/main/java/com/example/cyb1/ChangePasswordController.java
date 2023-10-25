package com.example.cyb1;

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
public class ChangePasswordController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

@PostMapping("/changePassword")
public String changePassword(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // Pobierz nazwę użytkownika bieżącego zalogowanego użytkownika
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // Pobierz UserDetails za pomocą UserDetailsService
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

 // For debugging - print the userDetails to console (remove this in production)
 System.out.println("Reloaded UserDetails: " + userDetails);

    System.out.println("Authentication: " + authentication); 
    System.out.println("Username: " + userDetails.getUsername()); // Log username
    System.out.println("Stored password: " + userDetails.getPassword()); // Log stored password
    System.out.println("Stored password: " + userDetails.getPassword()); 
    System.out.println("Old password: " + oldPassword); // Log old password
    System.out.println("UserDetails: " + userDetails); 
    System.out.println("Explicit Password: " + ((UserDetails) authentication.getPrincipal()).getPassword());
    if (passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        inMemoryUserDetailsManager.changePassword(oldPassword, passwordEncoder.encode(newPassword));
        return "Password changed successfully!";
    } else {
        return "Old password is incorrect!";
    }
}
}