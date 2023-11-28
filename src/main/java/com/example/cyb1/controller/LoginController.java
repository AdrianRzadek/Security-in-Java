package com.example.cyb1.controller;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        // Generate a random picture captcha code using SimpleCaptcha
        Captcha captcha = new Captcha.Builder(200, 50)
                .addText()  // Use default text renderer
                .addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();

        // Store the captcha code in the session (you should use a more secure method in production)
        String captchaCode = captcha.getAnswer();
        model.addAttribute("captchaCode", captchaCode);

        // Store the captcha image in the session (you should use a more secure method in production)
        BufferedImage captchaImage = captcha.getImage();
        String captchaImageBase64 = convertImageToBase64(captchaImage);
        model.addAttribute("captchaImage", captchaImageBase64);

        return "login";
    }

    private String convertImageToBase64(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String captcha, @RequestParam String enteredCaptcha, Model model) {
        // Validate the entered captcha code
        if (isCaptchaValid(enteredCaptcha, model)) {
            // Add your login logic here
            // For demonstration purposes, simply printing a message
            System.out.println("Login successful!");

            // Redirect to the home page
            return "redirect:/home";
        } else {
            // Captcha is invalid, return to the login page with an error message
            model.addAttribute("error", "Invalid captcha code. Please try again.");
            // You may want to regenerate a new captcha and update the view model with the new image and code
            return "login";
        }
    }

    private boolean isCaptchaValid(String enteredCaptcha, Model model) {
        // Retrieve the actual captcha code from the session
        String actualCaptchaCode = (String) model.getAttribute("captchaCode");

        // You need to implement the validation logic based on your captcha library
        // In the case of SimpleCaptcha, you can compare the entered code with the actual code
        // stored in the session or another secure location
        // For demonstration purposes, assuming a simple string comparison
        return enteredCaptcha.equals(actualCaptchaCode);
    }
}
