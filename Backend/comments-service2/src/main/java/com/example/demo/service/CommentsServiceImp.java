package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comments;
import com.example.demo.repository.CommentsRepository;


@Service
public class CommentsServiceImp implements CommentsService {
	
	@Autowired
	CommentsRepository repo;

	@Override
	public List<Comments> getComments() {
		return repo.findAll();
	}

	@Override
	public Comments addComment(Comments comment) {
		return repo.save(comment);
	}

	@Override
	public List<Comments> getCommentsByPostid(Integer postid) {
		return repo.findAllByPostid(postid);
	}

}
