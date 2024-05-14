package com.theater.kobrin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    void storeExhibit(MultipartFile file, String fileName);
    void storeProfile(MultipartFile file, String fileName);
    void init();
    void deleteAllExhibit();
    boolean deleteExhibit(File path);

    void deleteAllProfile();
}
