package com.example.cyb1.controller;



import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;

import java.io.IOException;

import javax.imageio.ImageIO;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @GetMapping
    public void generateCaptcha(HttpServletResponse response) throws IOException {
        // Create a captcha object
        Captcha captcha = new Captcha.Builder(200, 50)
                .addBackground(new FlatColorBackgroundProducer(Color.WHITE))
                .addText(new DefaultTextProducer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();

        // Set the response headers
        response.setContentType("image/png");
        response.setHeader("cache-control", "no-store, no-cache, must-revalidate");

        // Write the image to the response stream
        ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
    }
}