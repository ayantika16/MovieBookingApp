package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.configuration.JwtRequestFilter;

@SpringBootApplication
public class UserMovieAppApplication {
	
	@Bean
	public FilterRegistrationBean jwtFilter()
	{
	 FilterRegistrationBean fb = new FilterRegistrationBean();
	 fb.setFilter(new JwtRequestFilter());
	 fb.addUrlPatterns("/api/v1/*");
	 return fb;
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(UserMovieAppApplication.class, args);
	}

}
