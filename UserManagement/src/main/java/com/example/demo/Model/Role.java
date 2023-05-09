package com.example.demo.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleid;
   // @Enumerated(EnumType.STRING)
    private String roleName;
    private String roleDescription;
    
//	public RoleType getRoleName() {
//		return roleName;
//	}
//	public void setRoleName(RoleType roleName) {
//		this.roleName = roleName;
//	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

    
}
