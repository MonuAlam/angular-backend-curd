package com.curd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.curd.model.request.JwtAuthenticationRequest;
import com.curd.model.response.JwtAuthenticationDto;
import com.curd.service.JwtAuthenticationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

	@Autowired
	private JwtAuthenticationService service;
	
	@PostMapping("/login")
	public JwtAuthenticationDto login(@RequestBody JwtAuthenticationRequest request) {
	
		System.out.println(service.verify(request));
		return service.verify(request);
	}
}

