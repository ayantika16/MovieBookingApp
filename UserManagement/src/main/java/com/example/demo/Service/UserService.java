package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Model.User;
import com.example.demo.exception.SecretNameMismatchException;
import com.example.demo.exception.UserNotFoundException;

public interface UserService 
{
	public User addUser(User user);// user registration
	
	public boolean loginUser(String username, String password);// login
	
	public List<User> getAllUsers();// will be visible only if you are logged in
	
	public void initRoleAndUser();
	
	public Optional<User> getUserByName(String username);
	
	public String forgotPassword(String username) throws UserNotFoundException;
	
	public User updatePassword(String name, String secret, String password) throws UserNotFoundException, SecretNameMismatchException;
	

}
