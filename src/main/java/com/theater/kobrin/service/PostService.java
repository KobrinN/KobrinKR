package com.theater.kobrin.service;

import com.theater.kobrin.dto.PostDto;
import com.theater.kobrin.dto.PostRequest;
import com.theater.kobrin.entity.Post;

import java.util.List;

public interface PostService {
    Post add(PostRequest request, String username);

    List<PostDto> getAll();

    PostDto getById(Long id);
}
