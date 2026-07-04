package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Posts;

@DataJpaTest
@ActiveProfiles("uat")
class PostsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    void testFindAll() {
        Posts p1 = new Posts(1, "Pratham", "Content1", "Title1");
        Posts p2 = new Posts(2, "John", "Content2", "Title2");
        Posts p3 = new Posts(3, "Alice", "Content3", "Title3");

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);

        List<Posts> posts = postsRepository.findAll();

        assertEquals(3, posts.size());
        assertEquals("Pratham", posts.get(0).getAuthor());
    }

    @Test
    void testFindById() {
        Posts p1 = new Posts(10, "Pratham", "Java Content", "Java Title");

        entityManager.persist(p1);

        Optional<Posts> result = postsRepository.findById(10);

        assertEquals(true, result.isPresent());
        assertEquals(10, result.get().getPostid());
    }

    @Test
    void testDeleteById() {
        Posts p1 = new Posts(20, "Pratham", "Spring Boot", "REST API");

        entityManager.persist(p1);

        Posts found = entityManager.find(Posts.class, 20);
        entityManager.remove(found);

        Posts deleted = entityManager.find(Posts.class, 20);

        assertNull(deleted);
    }
}