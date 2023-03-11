package com.blitzstriker.blogapi.repository;

import com.blitzstriker.blogapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
