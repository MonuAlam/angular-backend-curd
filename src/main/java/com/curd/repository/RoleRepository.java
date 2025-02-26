package com.curd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curd.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
