package com.curd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.curd.model.request.JwtAuthenticationRequest;
import com.curd.model.response.JwtAuthenticationDto;
import com.curd.service.JwtAuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@Tag(name = "Login API")
public class JwtAuthenticationController {

	@Autowired
	private JwtAuthenticationService service;
	
	@PostMapping("/login")
	  @Operation(summary = "Authenticate User", description = "Authenticate user and return JWT token along with user roles")
    @ApiResponse(
        responseCode  = "200",
        description  = "User authenticated successfully",
        content  = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(value = "{\"username\": \"Monu\", \"success\": true, \"jwtToken\": \"eyJhbGciOiJIUzI1NiJ9...\", \"message\": \"Login Successful\", \"roles\": [{\"id\": 1, \"name\": \"ADMIN\"}]}"))
        
    )
	public JwtAuthenticationDto login(@RequestBody JwtAuthenticationRequest request) {
	
		System.out.println(service.verify(request));
		return service.verify(request);
	}
}

