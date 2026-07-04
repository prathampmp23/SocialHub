package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comments;
import com.example.demo.service.CommentsService;



@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	CommentsService service;
	
	@GetMapping("/port")
	public String getPort() {
		return "Comments running on port: " + port; 
	}

	@GetMapping
	public List<Comments> getComments() {
		return service.getComments();
	}
	
	@PostMapping
	public Comments addComment(@RequestBody Comments comment) {
		return service.addComment(comment);
	}
	
	@GetMapping("{postid}")
	public List<Comments> getCommentsByPostid(@PathVariable Integer postid) {
		return service.getCommentsByPostid(postid);
	}
	
}
