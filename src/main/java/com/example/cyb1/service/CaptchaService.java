package com.example.cyb1.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

public class CaptchaService {

    Captcha captcha = createCaptcha(240, 70);
	String readableImage = encodeCaptcha(captcha);
    private static final char[] DEFAULT_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	public static Captcha createCaptcha(Integer width, Integer height) {

		return new Captcha.Builder(width, height)
        .addBackground(new GradiatedBackgroundProducer())
				.addText(new DefaultTextProducer(6, DEFAULT_CHARACTERS), new DefaultWordRenderer())
                .gimp(new FishEyeGimpyRenderer())
                .addNoise()
				.build();
	}
    
	public static String encodeCaptcha(Captcha captcha) {
		String image = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(), "jpg", bos);
			byte[] byteArray = Base64.getEncoder().encode(bos.toByteArray());
			image = new String(byteArray);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return image;
}
	
	
}



