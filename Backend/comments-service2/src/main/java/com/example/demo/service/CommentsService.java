package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Comments;


public interface CommentsService {
	public List<Comments> getComments();
	public Comments addComment(Comments comment);
	public List<Comments> getCommentsByPostid(Integer postid);
	
}