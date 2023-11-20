package com.example.cyb1.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.web.bind.annotation.RequestParam;


public class CustomAuthenticationProvider  {
   

   private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION = 10 * 60 * 1000; // 10 minutes

    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private final Map<String, Long> lockoutTime = new ConcurrentHashMap<>();

   
    public void incrementAttempts(@RequestParam("username") String username) {

        //UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        attempts.compute(username, (key, value) -> (value == null) ? 1 : value + 1);
        if (attempts.get(username) >= MAX_ATTEMPTS) {
            lockoutTime.put(username, System.currentTimeMillis() + LOCK_DURATION);
        }
    }

  
    public void resetAttempts(@RequestParam("username") String username) {
        attempts.remove(username);
        lockoutTime.remove(username);
    }

    
    public boolean isBlocked(@RequestParam("username") String username) {
        Long lockTime = lockoutTime.get(username);
        return lockTime != null && lockTime > System.currentTimeMillis();
    }
}