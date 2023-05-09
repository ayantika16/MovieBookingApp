package com.example.demo.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
