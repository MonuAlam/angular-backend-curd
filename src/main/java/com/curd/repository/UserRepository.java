package com.curd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curd.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByEmail(String email);

}
