package com.theater.kobrin.service;

import com.theater.kobrin.dto.ExhibitDto;
import com.theater.kobrin.dto.ExhibitRequest;
import com.theater.kobrin.entity.Exhibit;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ExhibitService {
    List<ExhibitDto> findAll();

    Exhibit add(ExhibitRequest request);

    Exhibit deleteById(Long id);

    ExhibitDto findById(Long id);

    Exhibit edit(ExhibitRequest request, Long id);
}
