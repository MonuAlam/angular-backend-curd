package com.curd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminapi")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminWelcome() {
		return "Hello, Admin Welcome ";
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('USER')")
	public String userWelcome() {
		return "Hello, User Welcome ";
	}
}
