package com.curd.controller;

import com.curd.service.CustomUserDetailsService;
import com.curd.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class AdminControllerTest {

	private MockMvc mockMvc;

	@Mock
	private JwtService jwtService;

	@Mock
	private CustomUserDetailsService userDetailsService;

	@InjectMocks
	private AdminController adminController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

		Mockito.when(jwtService.generateToken(Mockito.anyString())).thenReturn("mockToken");

		UserDetails adminUser = User.builder().username("admin@gmail.com").password("admin123").roles("ADMIN").build();

		UserDetails normalUser = User.builder().username("user@gmail.com").password("user123").roles("USER").build();

		Mockito.when(userDetailsService.loadUserByUsername("admin@gmail.com")).thenReturn(adminUser);
		Mockito.when(userDetailsService.loadUserByUsername("user@gmail.com")).thenReturn(normalUser);
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
	void testAdminWelcome() throws Exception {
		mockMvc.perform(get("/adminapi/admin")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Hello, Admin Welcome "));
	}

	@Test
	@WithMockUser(username = "user@gmail.com", roles = "USER")
	void testUserWelcome() throws Exception {
		mockMvc.perform(get("/adminapi/users")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Hello, User Welcome "));
	}

}
