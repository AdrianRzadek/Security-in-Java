package com.example.cyb1.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

@Controller

public class CaptchaController {
       private static final Logger logger  = LoggerFactory.getLogger(UsersList.class);

  @GetMapping("/captcha")
    public void generateCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        // Create a captcha object
        Captcha captcha = new Captcha.Builder(200, 50)
                .addBackground(new FlatColorBackgroundProducer(Color.WHITE))
                .addText(new DefaultTextProducer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();

        // Store the captcha code in the session
        session.setAttribute("captchaCode", captcha.getAnswer());
        logger.info("captcha get answer " + captcha.getAnswer());
        response.setContentType("image/png");
        response.setHeader("cache-control", "no-store, no-cache, must-revalidate");
        // Write the image to the response stream
        ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
    }
@GetMapping("/validate")
    public String checkCaptcha(@RequestParam("captchaCode") String enteredCaptcha, HttpSession session) {
        // Retrieve the actual captcha code from the session
           logger.info("Retrieve the actual captcha code from the session" );
        String actualCaptchaCode = (String) session.getAttribute("captchaCode");
    
        // Validate the entered captcha code
        if (enteredCaptcha.equals(actualCaptchaCode)) {
            // CAPTCHA is valid, you can add your logic here
             logger.info("CAPTCHA is valid" );
            return "redirect:/success";
        } else {
             logger.info("CAPTCHA is invalid" );
            // CAPTCHA is invalid, return to the login page with an error message
            return "login";
        }
    
}
}
