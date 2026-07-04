package com.example.demo.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (requestAttributes != null) {

			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

			String authHeader = request.getHeader("Authorization");

			// Forward the JWT token if present
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				template.header("Authorization", authHeader);
			}
		}
	}
}