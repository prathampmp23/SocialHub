package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Posts;
import com.example.demo.service.PostsService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PostsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	private PostsService service;

	@Test
	public void testGetPosts() throws Exception {
		Posts Posts1 = new Posts(10, "John Doe", "Packed with evidence-based strategies", "Atomic habits");
		Posts Posts2 = new Posts(12, "Pratham", "Programming in Java", "Java coding habits");
		
		List<Posts> posts = new ArrayList<>();
		posts.add(Posts1);
		posts.add(Posts2);

		Mockito.when(service.getPosts()).thenReturn(posts);

		mockMvc.perform(get("/api/posts"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].postid", is(10)))
				.andExpect(jsonPath("$[1].author", is("Pratham")));
	}

	@Test
	public void testAddPost() throws Exception {
	    Posts user1 = new Posts(23, "Pratham", "Programming in Java", "Java coding habits");

	    Mockito.when(service.addPost(Mockito.any(Posts.class))).thenReturn(user1);

	    mockMvc.perform(post("/api/posts")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"postid\":23,\"author\":\"Pratham\",\"content\":\"Programming in Java\",\"title\":\"Java coding habits\"}"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.postid", is(23)));
	}

}
