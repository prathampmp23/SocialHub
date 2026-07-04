package com.example.demo.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CommentDto;

@FeignClient(name="COMMENTS-SERVICE") // service name
public interface CommentProxy {
	
//	method signature only
	@GetMapping("/api/comments/{postid}")
	public List<CommentDto> getCommentsByPostid(@PathVariable Integer postid);
	
	@GetMapping("/api/comments/port")
	public String getPort();

}

