package com.curd.model.request;

import java.util.Set;
import com.curd.model.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for creating a new user")
public class UserRequest {

	@Schema(description = "The user's email address", example = "monualam@gmail.com")
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	@Schema(description = "The user's passworf", example = "Pass@#$123")
	@NotEmpty(message = "Password cannot be empty")
	private String password;

	@Schema(description = "The user's  name", example = "Monu Alam")
	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@Schema(description = "The user's phone number", example = "9875745660")
	@NotEmpty(message = "Phone number cannot be empty")
	private String phone;
	
    @Schema(description = "The role of the user", example = "ADMIN")
	private Set<Role> roles;
}
