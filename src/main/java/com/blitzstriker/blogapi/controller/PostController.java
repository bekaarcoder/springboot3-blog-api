package com.blitzstriker.blogapi.controller;

import com.blitzstriker.blogapi.payload.PostRequestDto;
import com.blitzstriker.blogapi.payload.PostResponseDto;
import com.blitzstriker.blogapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(postService.createPost(postRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(postService.updatePost(postRequestDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostByCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.getPostByCategory(id), HttpStatus.OK);
    }
}
