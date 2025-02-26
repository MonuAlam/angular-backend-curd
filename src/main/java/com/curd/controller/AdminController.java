package com.curd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/adminapi")
@Tag(name = "Admin APIs" ,description = "This contains Admin APIs")
public class AdminController {

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get greeting", description = "Returns a greeting message for Admin")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401",description = "Unauthorize Access"),
			@ApiResponse(responseCode = "200",description = "Success")
	})
	public String adminWelcome() {
		return "Hello, Admin Welcome ";
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get greeting", description = "Returns a greeting message for User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401",description = "Unauthorize Access"),
			@ApiResponse(responseCode = "200",description = "Success")
	})
	public String userWelcome() {
		return "Hello, User Welcome ";
	}
}
