package com.curd.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationRequest {

	@NotEmpty(message = "User email reuired")
	@Schema(description = "User email",example = "monu@gmail.com")
	private String email;

	@NotEmpty(message = "User password required")
	@Schema(description = "User password",example = "monu@$321")
	private String password;
}
