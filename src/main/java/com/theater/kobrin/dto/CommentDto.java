package com.theater.kobrin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long userId;
    private Long id;
    private String username;
    private String date;
    private String text;
}
