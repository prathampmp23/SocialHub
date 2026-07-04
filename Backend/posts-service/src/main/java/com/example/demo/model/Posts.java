package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Posts {
	@Id
	private Integer postid;
	private String author;
	private String title;
	private String description;
	
	public Integer getPostid() {
		return postid;
	}
	public void setPostid(Integer postid) {
		this.postid = postid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Posts(Integer postid, String author, String title, String description) {
		super();
		this.postid = postid;
		this.author = author;
		this.title = title;
		this.description = description;
	}
	public Posts() {
		super();
	}
	@Override
	public String toString() {
		return "Posts [postid=" + postid + ", author=" + author + ", title=" + title + ", description=" + description
				+ "]";
	}
	
}
