package com.example.cyb1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@RestController
public class UsersList {
    
    /**
     *
     */
    /*

    @Autowired
    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @PostMapping("/usersList")
    public String usersList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Reloaded UserDetails: " +  userDetailsManager.loadUserByUsername(username));
        System.out.println("Reloaded UserDetails: " + username);
        
        return username;  // Returning the username for demonstration purposes.
    }*/

  
        @Autowired
        private InMemoryUserDetailsManager userDetailsManager;
    
        @PostMapping("/usersList")
        public Collection<String> usersList() {
            try {
                Field usersField = InMemoryUserDetailsManager.class.getDeclaredField("users");
                usersField.setAccessible(true);  // Make the private field accessible
    
                Map<String, UserDetails> usersMap = (Map<String, UserDetails>) usersField.get(userDetailsManager);
    
                // Return all usernames
                return usersMap.keySet();
    
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error fetching user details", e);
            }
        

 }

}