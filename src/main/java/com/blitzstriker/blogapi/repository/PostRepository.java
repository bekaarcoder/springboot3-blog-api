package com.blitzstriker.blogapi.repository;

import com.blitzstriker.blogapi.entity.Category;
import com.blitzstriker.blogapi.entity.Post;
import com.blitzstriker.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);
}
