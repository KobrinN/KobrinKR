package com.theater.kobrin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ExhibitRequest {
    private String name;
    private String description;
    private MultipartFile image;
}
