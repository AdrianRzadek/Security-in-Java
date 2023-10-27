package com.example.cyb1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 @RestController
public class UserBlockController {
   

    
    @Autowired
    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
     @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @PostMapping("/Block")
    public String banUser(@RequestParam("userban") String userban) {
        System.out.println("banned user " + userban);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userban);
       String password = userDetails.getPassword();
        System.out.println("pasword " + password);
        // Create a new user details
        UserDetails newUserDetails = org.springframework.security.core.userdetails.User.builder()
        .username(userban)
        .password(passwordEncoder.encode(password))
        .roles("USER")
        .disabled(true)
        .build();

        userDetailsManager.updateUser(newUserDetails);
        
        if (userDetailsManager.userExists(userban)) {
            return "Banned!";
        }
        else {
            throw new UsernameNotFoundException("Not bannaned :/");
        }
    }

}
