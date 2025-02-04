package com.curd.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.curd.enums.UsersRoles;
import com.curd.model.entity.Role;
import com.curd.model.entity.Users;
import com.curd.model.request.UserRequest;
import com.curd.model.response.UserDto;
import com.curd.repository.RoleRepository;
import com.curd.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder bCryptPasswordEncoder;

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private UserService userService;

	private Users user;
	private Users user1;


	private Role userRole;
	
	private Role adminRole;

	private UserRequest request;
	
	private UserDto expectedUserDto;
	
	private UserDto expectedUserDto1;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);
		
		userRole = Role.builder()
				.id(1)
				.name(UsersRoles.USER.name())
				.build();
		
		adminRole = Role.builder()
				.id(2)
				.name(UsersRoles.ADMIN.name())
				.build();


			user = Users.builder()
				.id(1)
				.email("monu@gmail.com")
				.password("monu123")
				.name("Monu Alam")
				.phone("98765445678")
				.roles(new HashSet<Role>() {
					{
						add(userRole);
						add(adminRole);
					}
				}).build();
	
		user1 = Users.builder()
						.id(1)
						.email("monu1@gmail.com")
						.password("monu123")
						.name("Monu")
						.phone("98765445678")
						.roles(new HashSet<Role>() {
							{
								add(userRole);
								add(adminRole);
							}
						}).build();
		
		request = UserRequest.builder()
				.email("monu@gmail.com")
				.password("monu123")
				.name("Monu Alam")
				.phone("98765445678")
				.roles(new HashSet<Role>() {
					{
						add(new Role(1, "ROLE_USER"));
						add(new Role(2, "ROLE_ADMIN"));
					}
				}).build();

		 expectedUserDto = UserDto.builder()
				.id(1).email("monu@gmail.com")
				.password("monu123")
				.name("Monu Alam")
				.phone("98765445678")
				.roles(new HashSet<Role>() {
					{
						add(userRole);
						add(adminRole);
					}
				})
				.build();
		 
		 expectedUserDto1 = UserDto.builder()
				.id(1).email("monu1@gmail.com")
				.password("monu123")
				.name("Monu")
				.phone("98765445678")
				.roles(new HashSet<Role>() {
					{
						add(userRole);
						add(adminRole);
					}
				})
				.build();

		
	}

	@Test
	void testRegisterUser() {

		when(userRepository.findByEmail(request.getEmail())).thenReturn(null);

		when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("monu123");

		when(roleRepository.findByName(UsersRoles.USER.name())).thenReturn(userRole);

		when(userRepository.save(any(Users.class))).thenReturn(user);


		UserDto actualUserDto = userService.registerUser(request);

		assertNotNull(actualUserDto);
		assertEquals(expectedUserDto.getId(), actualUserDto.getId());
		assertEquals(expectedUserDto.getEmail(), actualUserDto.getEmail());
		assertEquals(expectedUserDto.getName(), actualUserDto.getName());
		assertEquals(expectedUserDto.getPhone(), actualUserDto.getPhone());
		assertEquals(expectedUserDto.getPassword(), actualUserDto.getPassword());
		assertEquals(expectedUserDto.getRoles(), actualUserDto.getRoles());

	}

	@Test
	void testGetAllUsers() {

	when(userRepository.findAll()).thenReturn(List.of(user,user1));
	
	List<UserDto> actualUserDto=userService.getAllUsers();
	
	assertEquals(expectedUserDto.getId(), actualUserDto.get(0).getId());
	assertEquals(expectedUserDto1.getId(), actualUserDto.get(1).getId());
	
	assertEquals(expectedUserDto.getName(), actualUserDto.get(0).getName());
	assertEquals(expectedUserDto1.getName(), actualUserDto.get(1).getName());
	
	assertEquals(expectedUserDto.getPassword(), actualUserDto.get(0).getPassword());
	assertEquals(expectedUserDto1.getPassword(), actualUserDto.get(1).getPassword());
	
	assertEquals(expectedUserDto.getPhone(), actualUserDto.get(0).getPhone());
	assertEquals(expectedUserDto1.getPhone(), actualUserDto.get(1).getPhone());
	
	assertEquals(expectedUserDto.getEmail(), actualUserDto.get(0).getEmail());
	assertEquals(expectedUserDto1.getEmail(), actualUserDto.get(1).getEmail());
	
	assertEquals(expectedUserDto.getRoles(), actualUserDto.get(0).getRoles());
	assertEquals(expectedUserDto1.getRoles(), actualUserDto.get(1).getRoles());
	
	}

}
