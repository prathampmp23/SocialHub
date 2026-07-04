package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication 
@EnableDiscoveryClient // Eureka client
@EnableFeignClients(basePackages = "com.example.demo.service") // FeignClient 
public class PostsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced // annotation for enabling load balancing and tell need to use eureka server
	RestTemplate getTemplate() {
		return new RestTemplate();
	}
}
