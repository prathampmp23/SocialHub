package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CommentDto;
import com.example.demo.model.Posts;
import com.example.demo.service.PostsService;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

	static final Logger logger = LoggerFactory.getLogger(PostsController.class);

	@Autowired
	PostsService service;

	@GetMapping
	public List<Posts> getPosts() {
		return service.getPosts();
	}

	@PostMapping
	public Posts addPost(@RequestBody Posts post) {
		return service.addPost(post);
	}

	@GetMapping("{postid}")
	@Retry(name = "comments", fallbackMethod = "dummyData")
	public List<CommentDto> getCommentsByPostid(@PathVariable Integer postid) {
		logger.info("Tried to connect to comments service");
		return service.getCommentsByPostid(postid);
	}

	@GetMapping("port")
	@Retry(name = "cpost")
	public String getPort() {
		return service.getPort();
	}

	public List<CommentDto> dummyData(Exception e) {
		List<CommentDto> data = new ArrayList<>();
		data.add(new CommentDto(1, "dummy comment-1", "commenter-1", 9));
		data.add(new CommentDto(2, "dummy comment-2", "commenter-2", 10));
		data.add(new CommentDto(3, "dummy comment-3", "commenter-3", 11));
		data.add(new CommentDto(4, "dummy comment-4", "commenter-4", 12));
		data.add(new CommentDto(5, "dummy comment-5", "commenter-5", 13));
		return data;
	}

}
