package com.example.demo.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.JwtResponse;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.exception.SecretNameMismatchException;
import com.example.demo.exception.UserNotFoundException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("auth/v1")
public class AuthenticationController 
{
	private Map<String, String> mapObj = new HashMap<String, String>();
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/addUser")
	public ResponseEntity<?> registerUser(@RequestBody User user)
	{
		if(userService.addUser(user)!=null)
	{
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
		return new ResponseEntity<String>("user registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//method to generate token inside login API
	public String generateToken(String username, String password) throws ServletException, Exception
	{
		String jwtToken;
		if(username ==null || password ==null)
		{
			throw new ServletException("Please enter valid credentials");
		}
		
		boolean flag = userService.loginUser(username, password);
		
		if(!flag)
		{
			throw new ServletException("Invalid credentials");
		}
		
		else
		{
			jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
						.setExpiration(new Date(System.currentTimeMillis()+300000))
						.signWith(SignatureAlgorithm.HS256, "secret key").compact();
						
		}
		
		return jwtToken;
		
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> loginUser(@RequestBody User user)
//	{
//	
//		try
//		{
//			String jwtToken = generateToken(user.getUsername(), user.getPassword());
//			mapObj.put("Message", "User successfully logged in");
//			mapObj.put("Token:", jwtToken);
//			
//		}
//		catch(Exception e)
//		{
//			mapObj.put("Message", "User not logged in");
//			mapObj.put("Token:", null);
//			e.printStackTrace();
//			return new ResponseEntity<>(mapObj, HttpStatus.UNAUTHORIZED);
//		}
//		
//		return new ResponseEntity<>(mapObj, HttpStatus.OK);
//	}
	
	
	@PostMapping({"/login"})
    public JwtResponse createJwtToken(@RequestBody User user) throws Exception {
		
		try
		{
			String jwtToken = generateToken(user.getUsername(), user.getPassword());
//			mapObj.put("Message", "User successfully logged in");
//			mapObj.put("Token:", jwtToken);
			
			Optional<User> userFull=userService.getUserByName(user.getUsername());
			
			return new JwtResponse(userFull.get(),jwtToken);
			
		}
		catch(Exception e)
		{
//			mapObj.put("Message", "User not logged in");
//			mapObj.put("Token:", null);
			e.printStackTrace();
//			return new ResponseEntity<>(mapObj, HttpStatus.UNAUTHORIZED);
			
		}
		return null;
		//return new ResponseEntity<>(mapObj, HttpStatus.OK);
       // return jwtService.createJwtToken(jwtRequest);
    }
	
	
	@GetMapping("/forgot/{name}")
	public ResponseEntity<?> forgotPassword(@PathVariable String name) throws UserNotFoundException{
		
		String str=userService.forgotPassword(name);
		
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
	
	@PutMapping("/forgot/{name}/updatePassword")
	public ResponseEntity<?> updatePassword(@PathVariable String name, @RequestBody User user) throws UserNotFoundException,SecretNameMismatchException{
		String petname=user.getPetname();
		String password=user.getPassword();
		
		User u=userService.updatePassword(name, petname, password);
		
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	
	
}















