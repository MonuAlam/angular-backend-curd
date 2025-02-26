package com.curd.model.response;

import java.util.Set;
import com.curd.model.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JwtAuthenticationDto {

	@Schema(description = "Authenticated user name",example = "monu@gmail.com")
	private String username;
	
	@Schema(description = "Authentication flag",example = "true")
	private Boolean success;
	
    @Schema(description = "The generated JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String jwtToken;
    
    @Schema(description = "Authentication message",example = "login succussful")
	private String message; 
    
    @Schema(description = "Authentication user role",example = "ADMIN")
	private Set<Role> roles;
}
