package com.curd.controller;

import com.curd.enums.UsersRoles;
import com.curd.model.entity.Role;
import com.curd.model.request.UserRequest;
import com.curd.model.response.UserDto;
import com.curd.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class UserControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;
	
	private UserRequest userRequest;
	
	private UserDto expectedUserDto;
	
	private Role userRole;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
		
		userRole = Role.builder()
				.id(1)
				.name(UsersRoles.USER.name())
				.build();
		
		userRequest = UserRequest.builder()
				.email("monu@gmail.com")
				.password("monu123")
				.name("Monu Alam")
				.phone("98765445678")
				.roles(new HashSet<Role>() {
					{
						add(new Role(1, "ROLE_USER"));
					}
				}).build();
		
		 expectedUserDto = UserDto.builder()
					.id(1)
					.email("monu@gmail.com")
					.password("monu123")
					.name("Monu Alam")
					.phone("98765445678")
					.roles(new HashSet<Role>() {
						{
							add(userRole);
						}
					})
					.build();
		 
	}
	
	@Test
	void testRegisterUser() throws JsonProcessingException, Exception {
		
		when(userService.registerUser(any(UserRequest.class))).thenReturn(expectedUserDto);
		
		mockMvc.perform(post("/user/register")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(userRequest)))
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.id").value(expectedUserDto.getId()))
		    .andExpect(jsonPath("$.email").value(expectedUserDto.getEmail()))
		    .andExpect(jsonPath("$.password").value(expectedUserDto.getPassword()))
		    .andExpect(jsonPath("$.name").value(expectedUserDto.getName()))
		    .andExpect(jsonPath("$.phone").value(expectedUserDto.getPhone())) 
		    .andExpect(jsonPath("$.roles[0].id").value(userRole.getId()))
		    .andExpect(jsonPath("$.roles[0].name").value(userRole.getName()));


		verify(userService,times(1)).registerUser(any(UserRequest.class));
	}
	
	@Test
	void testGetAllUsers() throws Exception {
		
		List<UserDto> userList=List.of(expectedUserDto);
		
		when(userService.getAllUsers()).thenReturn(userList);
		
		mockMvc.perform(get("/user"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(expectedUserDto.getId()))
		.andExpect(jsonPath("$[0].email").value(expectedUserDto.getEmail()))
		.andExpect(jsonPath("$[0].password").value(expectedUserDto.getPassword()))
		.andExpect(jsonPath("$[0].name").value(expectedUserDto.getName()))
		.andExpect(jsonPath("$[0].phone").value(expectedUserDto.getPhone()))
		.andExpect(jsonPath("$[0].roles[0].id").value(userRole.getId()))
		.andExpect(jsonPath("$[0].roles[0].name").value(userRole.getName()));
	}
	
	@Test
	void testGetUserById() throws Exception {
		when(userService.getUserById(1)).thenReturn(expectedUserDto);
		
		mockMvc.perform(get("/user/{id}",1))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(expectedUserDto.getId()))
		.andExpect(jsonPath("$.email").value(expectedUserDto.getEmail()))
		.andExpect(jsonPath("$.password").value(expectedUserDto.getPassword()))
		.andExpect(jsonPath("$.name").value(expectedUserDto.getName()))
		.andExpect(jsonPath("$.phone").value(expectedUserDto.getPhone()));
			
		verify(userService,times(1)).getUserById(1);
		
	}

}
