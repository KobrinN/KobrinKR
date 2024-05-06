package com.theater.kobrin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostDto {
    private Long id;
    private String name;
    private String description;
    private String text;
    private String image;
}
