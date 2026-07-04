package com.example.demo.dto;

public class CommentDto {
	Integer commentid;
	String comment;
	String commenter;
	Integer postid;

	public Integer getCommentid() {
		return commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommenter() {
		return commenter;
	}

	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}

	public Integer getPostid() {
		return postid;
	}

	public void setPostid(Integer postid) {
		this.postid = postid;
	}

	public CommentDto(Integer commentid, String comment, String commenter, Integer postid) {
		super();
		this.commentid = commentid;
		this.comment = comment;
		this.commenter = commenter;
		this.postid = postid;
	}

	public CommentDto() {
		super();
	}

	@Override
	public String toString() {
		return "comments [commentid=" + commentid + ", comment=" + comment + ", commenter=" + commenter + ", postid="
				+ postid + "]";
	}
}
