package com.example.cyb1.controller;

import org.springframework.stereotype.Controller;

import com.example.cyb1.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
