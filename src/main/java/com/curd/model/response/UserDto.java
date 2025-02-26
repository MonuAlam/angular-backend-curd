package com.curd.model.response;

import java.util.Set;

import com.curd.model.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "The unique ID of the note", example = "1")
	private Integer id;
    
    @Schema(description = "User email address", example = "monu@gmail.com")
	private String email;
    
    @Schema(description = "User password",example = "hello@#43")
	private String password;
    
    @Schema(description = "User name",example = "Rohit Yadav")
	private String name;
    
    @Schema(description = "User phone number",example = "9875696576")
	private String phone;
    
    @Schema(description = "User roles",example = "ADMIN")
	private Set<Role> roles;
}
