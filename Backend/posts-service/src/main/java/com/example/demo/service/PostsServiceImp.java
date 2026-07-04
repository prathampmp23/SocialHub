package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CommentDto;
import com.example.demo.model.Posts;
import com.example.demo.repository.PostsRepository;

@Service
public class PostsServiceImp implements PostsService {

	@Autowired
	PostsRepository repo;

//	@Autowired
//	RestTemplate template; 
	
	
//	FeignClient
	@Autowired
	CommentProxy proxy; 
	
//	Eureka server name 
	String url = "http://COMMENTS-SERVICE/api/comments/"; 


	@Override
	public List<Posts> getPosts() {
		return repo.findAll();
	}

	@Override
	public Posts addPost(Posts post) {
		return repo.save(post);
	}

	@Override
	public List<CommentDto> getCommentsByPostid(Integer postid) {
//		String url = "http://localhost:8082/api/comments/"; // without Discovery Service
		
//		Using Eureka Client
//		return template.getForObject(url + postid, List.class);
		
//		Using FeignClient
		return proxy.getCommentsByPostid(postid);
	}

	@Override
	public String getPort() {
//		Using Eureka Client
//		return template.getForObject(url+"port", String.class);
		
//		Using FeignClent
		return proxy.getPort();
	}

}
