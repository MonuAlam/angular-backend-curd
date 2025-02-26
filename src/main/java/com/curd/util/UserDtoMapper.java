package com.curd.util;

import com.curd.model.entity.Users;
import com.curd.model.response.UserDto;

public class UserDtoMapper {

public static UserDto toResponseDto(Users user) {
		
		return UserDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.name(user.getName())
				.phone(user.getPhone())
				.password(user.getPassword())
				.roles(user.getRoles())
				.build();
	}
}
