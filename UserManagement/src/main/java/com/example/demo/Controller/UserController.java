package com.example.demo.Controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;


@RestController
@RequestMapping("api/v1")
public class UserController
{
	@Autowired
	private UserService uService;
	
	@PostConstruct
    public void initRoleAndUser() {
        uService.initRoleAndUser();
    }
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> userlist = uService.getAllUsers();
		
		if(userlist!=null)
		{
			return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
		}
		return new ResponseEntity<String>("userlist is empty", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping({"/forAdmin"})
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    public String forUser(){
        return "This URL is only accessible to the user";
    }
	
	
	

}
