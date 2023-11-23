package com.example.cyb1.controller;

import com.example.cyb1.service.CaptchaService;

import cn.apiclub.captcha.Captcha;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public String getCaptcha(Model model) {
    
        
        // Create a new Captcha
        Captcha captcha = CaptchaService.createCaptcha(240, 70);
        
        // Encode the Captcha as a base64 string
        String realCaptcha = CaptchaService.encodeCaptcha(captcha);
        
        // Set model attributes
        model.addAttribute("realCaptcha", realCaptcha);
        model.addAttribute("hiddenCaptcha", "yourHiddenCaptchaValue"); // Set the hidden captcha value
        model.addAttribute("captcha", ""); // You may set an initial value for the text input if needed
        
        return "captcha"; // Return the name of your Thymeleaf template (e.g., "captcha.html")
    }
}
