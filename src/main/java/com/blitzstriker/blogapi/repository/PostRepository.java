package com.blitzstriker.blogapi.repository;

import com.blitzstriker.blogapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
