package com.theater.kobrin.service;

import com.theater.kobrin.dto.CommentDto;
import com.theater.kobrin.dto.CommentRequest;
import com.theater.kobrin.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> findByExhibitId(Long id);

    Comment add(Long exhibitId, CommentRequest commentRequest);

    Comment delete(Long id);
}
