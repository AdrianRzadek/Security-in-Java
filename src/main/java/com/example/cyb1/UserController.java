package com.example.cyb1;

import com.example.cyb1.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/change-username")
    public String changeUsername(@RequestParam String oldUsername, @RequestParam String newUsername) {
        userService.changeUsername(oldUsername, newUsername);
        // Redirect to a success page or home page after the change
        return "redirect:/";
    }
}
