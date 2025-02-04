package com.curd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curd.model.request.UserRequest;
import com.curd.model.response.UserDto;
import com.curd.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public UserDto registerUser(@RequestBody UserRequest request) {

		return userService.registerUser(request);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public List<UserDto> getAllUsers() {

		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable Integer id) {

		return userService.getUserById(id);
	}

	@GetMapping("/profile")
	public UserDto getProfile() {

		return userService.getProfile();
	}

	@PutMapping("/update/{id}")
	public UserDto updateProfile(@PathVariable Integer id, @RequestBody UserRequest request) {

		return userService.updateProfile(id, request);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public UserDto deleteById(@PathVariable Integer id) {

		return userService.deleteById(id);

	}
}
