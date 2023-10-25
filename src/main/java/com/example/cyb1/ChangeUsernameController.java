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
public class ChangeUsernameController {

    private final InMemoryUserDetailsManager userDetailsManager;

@PostMapping("/changeUsername")
    public String changeUsername(String oldUsername, String newUsername) {
        // Check if old user exists
        if (!userDetailsManager.userExists(oldUsername)) {
            throw new UsernameNotFoundException("User not found: " + oldUsername);
        }

        // Fetch the existing user details
        UserDetails oldUserDetails = userDetailsManager.loadUserByUsername(oldUsername);

        // Create a new user details with the new username but same other attributes
        UserDetails newUserDetails = org.springframework.security.core.userdetails.User.builder()
                .username(newUsername)
                .password(oldUserDetails.getPassword())
                .roles(oldUserDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .toArray(String[]::new))
                .build();

        // Delete the old user and create the new user
        userDetailsManager.deleteUser(oldUsername);
        userDetailsManager.createUser(newUserDetails);
        return "Username changed successfully!";
    }
}