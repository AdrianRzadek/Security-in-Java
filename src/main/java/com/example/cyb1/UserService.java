package com.example.cyb1;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final InMemoryUserDetailsManager userDetailsManager;

    public UserService(InMemoryUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public void changeUsername(String oldUsername, String newUsername) {
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
    }
}