package com.curd.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	
	@Bean
	public OpenAPI openAPI() {
	
		return new OpenAPI().info(
				new Info().title("CURD Application")
				          .description("This Notes CURD application using angular and springBoot and Security")
				          .contact(new Contact().url("https://monuportfolio.vercel.app/")
				        		                 .email("monualam.edu@gmail.com")
				        		   ).version("V1.0")
			    	).servers(List.of(
							new Server().url("http://localhost:8082").description("This is local server"),
							new Server().url("http://localhost:8080/").description("This is Live Server"))
							);
		
	}
}
