package com.blitzstriker.blogapi.service.impl;

import com.blitzstriker.blogapi.entity.Post;
import com.blitzstriker.blogapi.exception.ResourceNotFoundException;
import com.blitzstriker.blogapi.payload.PostRequestDto;
import com.blitzstriker.blogapi.payload.PostResponseDto;
import com.blitzstriker.blogapi.repository.PostRepository;
import com.blitzstriker.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        post.setContent(postRequestDto.getContent());

        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = getPost(id);
        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto postRequestDto, Long id) {
        Post post = getPost(id);

        post.setTitle(postRequestDto.getTitle() != null ? postRequestDto.getTitle() : post.getTitle());
        post.setDescription(postRequestDto.getDescription() != null ? postRequestDto.getDescription() : post.getDescription());
        post.setContent(postRequestDto.getContent() != null ? postRequestDto.getContent() : post.getContent());

        Post updatedPost = postRepository.save(post);

        return modelMapper.map(updatedPost, PostResponseDto.class);
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPost(id);

        postRepository.delete(post);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
    }
}
