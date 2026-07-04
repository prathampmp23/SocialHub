package com.example.demo.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter that performs JWT-based authentication for incoming requests.
 * <p>
 * Extracts the token from the Authorization header, validates it,
 * and sets the authentication in the security context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    /**
     * Processes incoming HTTP requests and applies JWT validation.
     *
     * @param request incoming HTTP request
     * @param response HTTP response
     * @param chain filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        handleError(response, HttpStatus.UNAUTHORIZED, "JWT token is missing or invalid");
	        return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            role = role.replace("[", "").replace("]", "");

            jwtService.validateToken(token);

            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            handleError(response, HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");
            return;
        }

        chain.doFilter(request, response);
    }
    
    private void handleError(HttpServletResponse response, HttpStatus status, String message) throws IOException {

		response.setStatus(status.value());
		response.setContentType("application/json");
		
		String json = String.format(
			"{ \"status\": %d, \"error\": \"%s\" }",
			status.value(),
			message
		);
		
		response.getWriter().write(json);
    }

    /**
     * Determines whether the filter should be applied.
     * <p>
     * Used to exclude certain endpoints such as API documentation
     * from authentication.
     *
     * @param request incoming HTTP request
     * @return {@code true} if the filter should be skipped
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        		return path.contains("/v3/api-docs")
        		        || path.contains("/swagger-ui")
        		        || path.contains("/swagger-ui.html")
        		        || path.contains("/webjars")
        		        || path.contains("/h2-console");

    }
}