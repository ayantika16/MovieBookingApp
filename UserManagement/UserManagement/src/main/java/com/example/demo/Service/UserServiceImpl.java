package com.example.demo.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;

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
		System.out.println("User: "+ user1.getUsername());
		if(user1!=null)
		{
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
	        adminRole.setRoleName("Admin");
	        adminRole.setRoleDescription("Admin role");
	        roleRepo.save(adminRole);

	        Role userRole = new Role();
	        userRole.setRoleName("User");
	        userRole.setRoleDescription("Default role for newly created record");
	        roleRepo.save(userRole);

	        User adminUser = new User();
	        //adminUser.setId(1);
	        adminUser.setUsername("ayantika.admin");
	        adminUser.setPassword("password@admin");
	        adminUser.setEmail("ayantika@gmail.com");
	        adminUser.setPetname("dog");
	        Set<Role> adminRoles = new HashSet<>();
	        adminRoles.add(adminRole);
	        adminUser.setRole(adminRoles);
	        userRepo.save(adminUser);
	        
	        
	        User normalUser = new User();
	        normalUser.setUsername("ayantika.user");
	        normalUser.setPassword("password@user");
	        normalUser.setEmail("ayantika@gmail.com");
	        normalUser.setPetname("cat");
	        Set<Role> userRoles = new HashSet<>();
	        userRoles.add(userRole);
	        normalUser.setRole(userRoles);
	        userRepo.save(normalUser);
	        

	    }

	
}














