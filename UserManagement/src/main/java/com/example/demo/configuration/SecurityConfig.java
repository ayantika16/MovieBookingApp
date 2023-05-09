//package com.example.demo.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//		
//		httpSecurity.csrf().disable()
//		.authorizeHttpRequests()
//		.antMatchers("/api/v1/forAdmin","/api/v1/getAllUsers").hasAuthority("ADMIN")
//		.antMatchers("/api/v1/forUser","/api/v1/getAllUsers").hasAuthority("USER")
//		.antMatchers("/auth/v1/addUser","/auth/v1/login").permitAll()
//		.anyRequest()
//		.authenticated();
//		
//		return httpSecurity.build();
//	}
//
//}
