package com.blitzstriker.blogapi.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
