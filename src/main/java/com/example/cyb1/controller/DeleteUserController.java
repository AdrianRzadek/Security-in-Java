package com.example.cyb1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteUserController {

     private static final Logger logger 
      = LoggerFactory.getLogger(DeleteUserController.class);


    @Autowired
    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userName") String userName) {

        if (userDetailsManager.userExists(userName)) {
            userDetailsManager.deleteUser(userName);
            
            logger.info("User " + userName + " successfully deleted");
            return "User " + userName + " successfully deleted";
        }
        else {
             logger.info("Username not found");
            return "Username not found";
        }
    }
}