package com.curd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;


@RestController
@RequestMapping("/user")
@Tag(name = "User APIs",description = "Operation related to Users")
public class UserController {

	@Autowired
	private UserService userService;

	
	@PostMapping("/register")
	@Operation(summary = "Register User",description = "Register a new User")
	@ApiResponse(
			responseCode = "201",
            description = "User successfully registered",
            content = @Content(
            		mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\"}")
            		
            		)
			)
	public UserDto registerUser(@RequestBody UserRequest request) {

		return userService.registerUser(request);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	@Operation(summary = "Get All User",description = "Get List of all Users only access by ADMIN")
	 @ApiResponse(
	            responseCode = "200", 
	            description = "Successfully fetched the list of users",
	            content = @Content(
	                    mediaType = "application/json",
	                    examples = @ExampleObject(value = "[{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\"}, {\"id\": 2, \"email\": \"jane.smith@example.com\", \"name\": \"Jane Smith\", \"phone\": \"0987654321\"}]")
	            )
	    )
	public List<UserDto> getAllUsers() {

		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get User",description = "Get User By it's Id")
	 @ApiResponse(
	            responseCode = "200", 
	            description = "User details fetched successfully",
	            content = @Content(
	                    mediaType = "application/json",
	                    examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\", \"roles\": [\"ADMIN\", \"USER\"]}")
	            )
	    )
	    @ApiResponse(
	            responseCode = "404",
	            description = "User not found",
	            content = @Content(mediaType = "application/json")
	    )
	public UserDto getUserById(@PathVariable Integer id) {

		return userService.getUserById(id);
	}
	

	@GetMapping("/profile")
	@Operation(summary = "Get User",description = "Get User by its login Credential")
	 @ApiResponse(
	            responseCode = "200", 
	            description = "User profile fetched successfully",
	            content = @Content(
	                    mediaType = "application/json",
	                    examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\"}")
	            )
	    )
	public UserDto getProfile() {

		return userService.getProfile();
	}

	@PutMapping("/update/{id}")
	@Operation(summary = "Update User",description = "Update User Details by it's Id")
	  @ApiResponse(
	            responseCode = "200", 
	            description = "User details successfully updated",
	            content = @Content(
	                    mediaType = "application/json",
	                    examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\"}")
	            )
	    )
	    @ApiResponse(
	            responseCode = "404", 
	            description = "User not found",
	            content = @Content(mediaType = "application/json")
	    )
	public UserDto updateProfile(@PathVariable Integer id, @RequestBody UserRequest request) {

		return userService.updateProfile(id, request);
	}
	

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete User",description = "Delete User by it's Id")
    @ApiResponse(
            responseCode = "200", 
            description = "User successfully deleted",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john.doe@example.com\", \"name\": \"John Doe\", \"phone\": \"1234567890\"}")
            )
    )
    @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content(mediaType = "application/json")
    )

	public UserDto deleteById(@PathVariable Integer id) {

		return userService.deleteById(id);

	}
}
