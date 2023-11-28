package com.example.cyb1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class UserChangePasswordController {
     private static final Logger logger  = LoggerFactory.getLogger(UserChangePasswordController.class);
     


   
     @Value("${google.recaptcha.site-key}")
     private String recaptchaSiteKey;
     
     @Value("${google.recaptcha.secret-key}")
     private String recaptchaSecretKey;

   
     @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/userChangePassword")
    public String userChangePassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("g-recaptcha-response") String recaptchaResponse) {
        
         // Validate reCAPTCHA response
         if (!validateRecaptcha(recaptchaResponse)) {
            logger.info("reCAPTCHA validation failed");
            return "reCAPTCHA validation failed";
        }
        
                                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        if (hasConsecutiveCharacters(newPassword)) {
            logger.info("Password should contain any sign only once!");
            return "Password should contain any sign only once!";
        }

        if (passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            InMemoryUserDetailsManager inMemoryUserDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
            inMemoryUserDetailsManager.updatePassword(userDetails, passwordEncoder.encode(newPassword));
             logger.info("Password changed successfully!");
            return "Password changed successfully!";
        } else {
             logger.info("Old password is incorrect!");
            return "Old password is incorrect!";
        }
    }

    private boolean validateRecaptcha(String recaptchaResponse) {
        // Perform reCAPTCHA validation here using the recaptchaSecretKey and recaptchaResponse
        // You may use a library or make an HTTP request to Google's reCAPTCHA API for validation.
        // If validation succeeds, return true; otherwise, return false.
        // Example: You can use a library like google-recaptcha in your build tool.

        // Replace the following line with your reCAPTCHA validation logic
        return true;
    }

    private boolean hasConsecutiveCharacters(String password) {
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                  logger.info("found consecutive characters");
                return true; // found consecutive characters
            }
        }
         logger.info("Not found consecutive characters");
        return false;
    }
}
