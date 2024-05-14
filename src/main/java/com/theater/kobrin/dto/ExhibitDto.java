package com.theater.kobrin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ExhibitDto {
    private Long id;
    private String name;
    private String description;
    private String image;
}
