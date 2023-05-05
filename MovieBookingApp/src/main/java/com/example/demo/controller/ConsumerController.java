package com.example.demo.controller;


import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.JwtRequest;

@RestController
@RequestMapping("call/consumer")
public class ConsumerController 
{
	@PostMapping(value="/login")
	public ResponseEntity<?> consumeLogin(@RequestBody JwtRequest userdto) throws RestClientException,Exception
	{//String baseUrl ="http://localhost:8084/auth/v1/login";
		
		String baseUrl ="http://localhost:9090/login";
	
	RestTemplate restTemplate = new RestTemplate();
	
	ResponseEntity<Map<String,String>> result =null;
	try
	{
result=restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userdto), new ParameterizedTypeReference<Map<String,String>>(){});
	}
	catch(Exception e)
	{
		return new ResponseEntity<String>("Login was not successful", HttpStatus.UNAUTHORIZED);
		
	}
	return new ResponseEntity<Map<String,String>>(result.getBody(), HttpStatus.OK);
		
	}
	
	private static HttpEntity<JwtRequest> getHeaders(JwtRequest userdto)
	{
		HttpHeaders header = new HttpHeaders();
		
		header.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<JwtRequest>(userdto, header);
	}

}











