package com.curd.service;

import java.net.Authenticator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.curd.enums.UsersRoles;
import com.curd.model.entity.Role;
import com.curd.model.entity.Users;
import com.curd.model.request.UserRequest;
import com.curd.model.response.UserDto;
import com.curd.repository.RoleRepository;
import com.curd.repository.UserRepository;
import com.curd.util.UserDtoMapper;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	public UserDto registerUser(UserRequest request) {
		
		Users existingUsers =userRepository.findByEmail(request.getEmail());
				
		if (existingUsers!=null) {
			throw new RuntimeException("Please use different email id");
		}
		
		Users user = toEntity(request);
		
		return UserDtoMapper.toResponseDto(userRepository.save(user));
	}


	public Users toEntity(UserRequest request) {
		
	    Set<Role> roles = new HashSet<>();


	    if (request.getRoles() != null && !request.getRoles().isEmpty()) {
	        for (Role role : request.getRoles()) {

	            Role roleEntity = roleRepository.findByName(role.getName());
	            if (roleEntity == null) {

	                roleEntity = new Role(null, role.getName());
	                roleRepository.save(roleEntity);
	            }
	            roles.add(roleEntity);
	        }
	    } else {

	        Role defaultRole = roleRepository.findByName(UsersRoles.USER.name());
	        if (defaultRole == null) {

	            defaultRole = new Role(null, UsersRoles.USER.name());
	            roleRepository.save(defaultRole);
	        }
	        roles.add(defaultRole);
	    }


	    return Users.builder()
	            .email(request.getEmail())
	            .password(bCryptPasswordEncoder.encode(request.getPassword()))
	            .name(request.getName())
	            .phone(request.getPhone())
	            .roles(roles)
	            .build();
	}


	public List<UserDto> getAllUsers() {

		return userRepository.findAll().stream().map(UserDtoMapper::toResponseDto).toList();
	}


	public UserDto getProfile() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		Users users = userRepository.findByEmail(email);

		return UserDtoMapper.toResponseDto(users);
	}


	public UserDto updateProfile(Integer id, UserRequest request) {

		Users users=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
		
		Users updatedUsers=updateWithBuilder(users, request);
		
		return UserDtoMapper.toResponseDto(userRepository.save(updatedUsers));
	}
	
	private Users updateWithBuilder(Users users,UserRequest request) {
		return users.toBuilder()
				.name(request.getName())
				.email(request.getEmail())
				.roles(request.getRoles())
				.phone(request.getPhone())
				.build();
	}


	public UserDto getUserById(Integer id) {

		Users users=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
		
		return UserDtoMapper.toResponseDto(users);
	}


	public UserDto deleteById(Integer id) {

		Users users=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
		
		userRepository.delete(users);
		
		return UserDtoMapper.toResponseDto(users);
		
	}
	
	
}
