package com.example.cyb1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/user").setViewName("user");
		registry.addViewController("/changePassword").setViewName("/changePassword");
		registry.addViewController("/changeUsername").setViewName("/changeUsername");
		registry.addViewController("/addUser").setViewName("/addUser");
		registry.addViewController("/deleteUser").setViewName("/deleteUser");
        registry.addViewController("/userChangePassword").setViewName("/userChangePassword");
		registry.addViewController("/Block").setViewName("/Block");
	}

}