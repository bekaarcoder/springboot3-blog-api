package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.entity.User;
import com.blitzstriker.blogapi.payload.PostRequestDto;
import com.blitzstriker.blogapi.payload.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(Long id);

    PostResponseDto updatePost(PostRequestDto postRequestDto, Long id);

    void deletePost(Long id);

    List<PostResponseDto> getPostByCategory(Long id);

    List<PostResponseDto> getPostByLoggedInUser();
}
