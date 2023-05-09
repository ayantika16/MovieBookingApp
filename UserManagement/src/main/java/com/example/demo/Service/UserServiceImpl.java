package com.example.demo.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Role;
import com.example.demo.Model.RoleType;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.SecretNameMismatchException;
import com.example.demo.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public User addUser(User user) {
		if(user!=null)
		{
			 Role role = roleRepo.findById(2).get();
		        Set<Role> userRoles = new HashSet<>();
		        userRoles.add(role);
		        user.setRole(userRoles);
		        
			return userRepo.saveAndFlush(user);
			
		}
		return null;
	}

	@Override
	public boolean loginUser(String username, String password) {
		
		User user1 = userRepo.validateUser(username, password);
		//System.out.println("User: "+ user1.getUsername());
		if(user1!=null)
		{
			System.out.println("User: "+ user1.getUsername());
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
	
		List<User> userList = userRepo.findAll();
		
		if(userList!=null & userList.size() >0)
		{
			return userList;
		}
		else
			return null;
	}
	
	@Override
	 public void initRoleAndUser() {

	        Role adminRole = new Role();
	        adminRole.setRoleName("ADMIN");
	        adminRole.setRoleDescription("Admin role");
	        roleRepo.save(adminRole);

	        Role userRole = new Role();
	        userRole.setRoleName("USER");
	        userRole.setRoleDescription("Default role for newly created record");
	        roleRepo.save(userRole);

	        User adminUser = new User();
	        //adminUser.setId(1);
	        adminUser.setUsername("admin.admin");
	        adminUser.setPassword("password@admin");
	        adminUser.setEmail("admin@gmail.com");
	        adminUser.setPetname("dog");
	        Set<Role> adminRoles = new HashSet<>();
	        adminRoles.add(adminRole);
	        adminUser.setRole(adminRoles);
	        userRepo.save(adminUser);
	        
	        
	        User normalUser = new User();
	        //normalUser.setId(2);
	        normalUser.setUsername("user.user");
	        normalUser.setPassword("password@user");
	        normalUser.setEmail("user@gmail.com");
	        normalUser.setPetname("cat");
	        Set<Role> userRoles = new HashSet<>();
	        userRoles.add(userRole);
	        normalUser.setRole(userRoles);
	        userRepo.save(normalUser);
	        

	    }
	
	public Optional<User> getUserByName(String username){
		
		return this.userRepo.findByUserName(username);
	}

	@Override
	public String forgotPassword(String username) throws UserNotFoundException{
		
		Optional<User> user=userRepo.findByUserName(username);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException();
		}
		
		return "Username Found. Please provide your petname for updating password";
	}
	
	@Override
	public User updatePassword(String name, String secret, String password) throws UserNotFoundException,SecretNameMismatchException{
		
		Optional<User> user=userRepo.findByUserName(name);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException();
		}
		
		User founduser=user.get();
//		System.out.println((founduser.getPetname()));
//		System.out.println(secret);
//		System.out.println(founduser.getPetname().equalsIgnoreCase(password));
		if(founduser.getPetname().equalsIgnoreCase(secret)) {
			founduser.setPassword(password);
		}else {
			throw new SecretNameMismatchException();
		}
		
		return userRepo.saveAndFlush(founduser);
		
		
	}
	
}














