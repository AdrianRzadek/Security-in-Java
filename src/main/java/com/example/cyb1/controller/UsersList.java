package com.example.cyb1.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@RestController
public class UsersList {
    
   private static final Logger logger  = LoggerFactory.getLogger(UsersList.class);
        @Autowired
        private InMemoryUserDetailsManager userDetailsManager;
    
        @PostMapping("/usersList")
        public Collection<String> usersList() {
            
            try {
                Field usersField = InMemoryUserDetailsManager.class.getDeclaredField("users");
                usersField.setAccessible(true);  // Make the private field accessible
    
                Map<String, UserDetails> usersMap = (Map<String, UserDetails>) usersField.get(userDetailsManager);
    
               var log = usersMap.keySet().toString();
                // Return all usernames
                logger.info(log + " info users list");
                return usersMap.keySet();
    
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error fetching user details", e);
            }
        

 }

}