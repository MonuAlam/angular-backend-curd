package com.curd.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.curd.filter.JwtFilter;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
//			AuthenticationEntryPoint jwtAuthentication) throws Exception {
//
//		httpSecurity.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/user/register").permitAll() 																					
//						.requestMatchers("/adminapi/admin", "/user").hasAuthority("ADMIN") 
//						.requestMatchers("/adminapi/users").hasAuthority("USER") 
//						.anyRequest().authenticated())
//				 
//				.httpBasic(httpBasic -> httpBasic.disable())
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //before validating username and pass from db validate JWT token first
//				.cors();
//		httpSecurity.securityContext(securityContext -> securityContext.requireExplicitSave(false))
//				.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthentication));
//
//		return httpSecurity.build();
//	}
//		
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, 
	        AuthenticationEntryPoint jwtAuthentication) throws Exception {

	    httpSecurity.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/user/register").permitAll()
	            		.requestMatchers("/swagger-ui/**", "/swagger-ui.html/**","/v3/api-docs/**","/webjars/**","/swagger-resources/**"	)
	            		.permitAll()
	                    .requestMatchers("/adminapi/admin", "/user","/notes/getall").hasAuthority("ADMIN")
	                    .requestMatchers("/adminapi/users").hasAuthority("USER")
	                    .anyRequest().authenticated())
	            .httpBasic(httpBasic -> httpBasic.disable())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) 
	            .cors(cors -> cors.configurationSource(corsConfigurationSource())); 

	    httpSecurity.securityContext(securityContext -> securityContext.requireExplicitSave(false))
	            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthentication));

	    return httpSecurity.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("https://angular-frontend-curd.vercel.app")); 
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration); 
	    return source; 
	}


	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());

		provider.setUserDetailsService(userDetailsService);

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder(12);
	}

}
