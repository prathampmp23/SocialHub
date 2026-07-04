package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CommentDto;
import com.example.demo.model.Posts;


public interface PostsService {
	public List<Posts> getPosts();
	public Posts addPost(Posts post);
	public List<CommentDto> getCommentsByPostid(Integer postid);
	public String getPort();
	
}