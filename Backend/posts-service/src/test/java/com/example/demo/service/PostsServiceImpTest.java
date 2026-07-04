package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Posts;
import com.example.demo.repository.PostsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostsServiceImpTest {

	@Mock
	private PostsRepository repo;

	@Mock
	private CommentProxy proxy;

	@InjectMocks
	private PostsServiceImp service;

	@Test
	void testGetPosts() {
		List<Posts> posts = new ArrayList<>();
		posts.add(new Posts(1, "Pratham", "Content1", "Title1"));
		posts.add(new Posts(2, "John", "Content2", "Title2"));

		when(repo.findAll()).thenReturn(posts);

		List<Posts> result = service.getPosts();

		assertEquals(2, result.size());
		assertEquals("Pratham", result.get(0).getAuthor());
	}

	@Test
	void testAddPost() {
		Posts post = new Posts(10, "Pratham", "Java Content", "Java Title");

		when(repo.save(post)).thenReturn(post);

		Posts saved = service.addPost(post);

		assertNotNull(saved);
		assertEquals(10, saved.getPostid());
		assertEquals("Pratham", saved.getAuthor());
	}
}
