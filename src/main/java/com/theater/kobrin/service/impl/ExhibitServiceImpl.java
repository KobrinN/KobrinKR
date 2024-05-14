package com.theater.kobrin.service.impl;

import com.theater.kobrin.config.FileUploadProperties;
import com.theater.kobrin.dto.ExhibitDto;
import com.theater.kobrin.dto.ExhibitRequest;
import com.theater.kobrin.entity.Exhibit;
import com.theater.kobrin.entity.Post;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.ExhibitRepo;
import com.theater.kobrin.service.ExhibitService;
import com.theater.kobrin.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitServiceImpl implements ExhibitService {
    private final ExhibitRepo exhibitRepo;
    private final FileUploadService fileUploadService;

    @Override
    public List<ExhibitDto> findAll() {
        return exhibitRepo.findAll().stream()
                .map(this::buildDto)
                .toList();
    }

    @Override
    public Exhibit add(ExhibitRequest request) {
        if (!StringUtils.hasText(request.getName()) ||
                !StringUtils.hasText(request.getDescription()) ||
                request.getImage().isEmpty()) throw new NotValidFieldException("EMPTY FIELDS!");

        Exhibit exhibit = new Exhibit();
        exhibit.setName(request.getName());
        exhibit.setDescription(request.getDescription());
        exhibit.setImage("");
        exhibitRepo.saveAndFlush(exhibit);
        String nameOfImage = "exhibit" + exhibit.getId() + ".jpg";
        fileUploadService.storeExhibit(request.getImage(), nameOfImage);
        exhibit.setImage(nameOfImage);

        exhibitRepo.saveAndFlush(exhibit);
        return exhibit;
    }

    @Override
    public Exhibit deleteById(Long id) {
        FileUploadProperties properties = new FileUploadProperties();
        Optional<Exhibit> exhibitFromDb = exhibitRepo.findById(id);
        if(exhibitFromDb.isEmpty()) throw new NotFoundException("NOT FOUND EXHIBIT!");
        exhibitRepo.deleteById(id);
        File image = new File(properties.getLocationExhibit() + "\\" + exhibitFromDb.get().getImage());
        Boolean flg = fileUploadService.deleteExhibit(image);
        return exhibitFromDb.get();
    }

    @Override
    public ExhibitDto findById(Long id) {
        Optional<Exhibit> exhibitFromDB = exhibitRepo.findById(id);
        if(exhibitFromDB.isEmpty()) throw new NotFoundException("NOT FOUND EXHIBIT!");
        return buildDto(exhibitFromDB.get());
    }

    @Override
    public Exhibit edit(ExhibitRequest request, Long id) {
        FileUploadProperties properties = new FileUploadProperties();
        if (!StringUtils.hasText(request.getName()) ||
                !StringUtils.hasText(request.getDescription())) throw new NotValidFieldException("EMPTY FIELDS!");

        Optional<Exhibit> exhibitFromDb = exhibitRepo.findById(id);
        if(exhibitFromDb.isEmpty()) throw new NotFoundException("NOT FOUND EXHIBIT!");

        Exhibit exhibit = exhibitFromDb.get();
        if(!request.getImage().isEmpty()){
            File image = new File(properties.getLocationExhibit() + "\\" + exhibit.getImage());
            Boolean flg = fileUploadService.deleteExhibit(image);
            fileUploadService.storeExhibit(request.getImage(), exhibit.getImage());
        }

        exhibit.setName(request.getName());
        exhibit.setDescription(request.getDescription());


        exhibitRepo.saveAndFlush(exhibit);
        return exhibit;
    }

    protected ExhibitDto buildDto(Exhibit exhibit){
        return ExhibitDto.builder()
                .id(exhibit.getId())
                .name(exhibit.getName())
                .description(exhibit.getDescription())
                .image("/images/"+exhibit.getImage())
                .build();
    }
}
