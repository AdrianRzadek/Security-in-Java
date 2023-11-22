package com.example.cyb1.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class OTP {
 @PostMapping("/OTP")
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
         double a = 2.0; 
        int numberOfUsers = 4;
        int numberOfRoles = 4;
        for (int i = 0; i < numberOfUsers * numberOfRoles; i++) {
            double x = i * 0.1; 
            int charIndex = (int) (a * Math.sin(x) * 10) % 10;
            password.append((char) ('a' + charIndex));
        }
         System.out.println( password.toString());
        return password.toString();
       
    }
    
        String generatedPassword = generatePassword();
      
    
}