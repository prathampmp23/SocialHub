package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
	List<Comments> findAllByPostid(Integer postid);
}
