//package com.curd;
//
//import com.curd.controller.UserController;
//import com.curd.model.request.UserRequest;
//import com.curd.model.response.UserDto;
//import com.curd.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    private UserRequest userRequest;
//    private UserDto userDto;
//
//    @BeforeEach
//    public void setUp() {
//        userRequest = new UserRequest();
//        userRequest.setEmail("testUser");
//        userRequest.setPassword("testPassword");
//
//        userDto = new UserDto();
//        userDto.setEmail("testUser");
//        userDto.setPassword("testPassword");
//    }
//
//    @Test
//    public void testRegisterUser() {
//        // Mocking the service layer
//        when(userService.registerUser(any(UserRequest.class))).thenReturn(userDto);
//
//        // Call the controller method
//        UserDto result = userController.registerUser(userRequest);
//
//        // Verify and assert the result
//        assertEquals("testUser", result.getEmail());
//        assertEquals("testPassword", result.getPassword());
//
//        // Verify that service method is called
//        verify(userService, times(1)).registerUser(any(UserRequest.class));
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        // Mocking the service layer
//        List<UserDto> userList = new ArrayList<>();
//        userList.add(userDto);
//
//        when(userService.getAllUsers()).thenReturn(userList);
//
//        // Call the controller method
//        List<UserDto> result = userController.getAllUsers();
//
//        // Verify the result
//        assertEquals(1, result.size());
//        assertEquals("testUser", result.get(0).getEmail());
//
//        // Verify that service method is called
//        verify(userService, times(1)).getAllUsers();
//    }
//}
