package com.curd.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.curd.service.CustomUserDetailsService;
import com.curd.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			token = authHeader.substring(7);
			username = jwtService.extractUserName(token);

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//check if user is not already authenticated
			UserDetails userDetails = 
					context.getBean(CustomUserDetailsService.class)
					.loadUserByUsername(username);

			if (jwtService.validateToken(token, userDetails)) {//validate token like expiration time and signature etc.

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());// This adds request-specific details (such as the remote address and session ID) to the authentication token.
				
				authToken.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));//This sets the Authentication object in the SecurityContextHolder, effectively authenticating the user for the current request.
				
				SecurityContextHolder.getContext()
				.setAuthentication(authToken);
			}

		}

		filterChain.doFilter(request, response); //After the authentication process, the filter chain is continued by calling doFilter() on the next filter in the chain (or the main controller if no other filters exist).
	}
	
}
