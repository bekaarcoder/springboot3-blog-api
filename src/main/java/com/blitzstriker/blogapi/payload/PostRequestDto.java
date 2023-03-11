package com.blitzstriker.blogapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    @NotEmpty(message = "Post title is required")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty(message = "Post description is required")
    @Size(min = 100, message = "Post description should be at least 100 characters")
    private String description;

    @NotEmpty(message = "Content is required")
    private String content;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
