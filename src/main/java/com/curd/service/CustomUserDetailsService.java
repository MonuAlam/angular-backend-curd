package com.curd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.curd.model.entity.Users;
import com.curd.model.entity.UserPrincipal;
import com.curd.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Users user = userRepository.findByEmail(email);

		if (user == null) {
			throw new RuntimeException("user not found");
		}

		return new UserPrincipal(user);
	}

}
