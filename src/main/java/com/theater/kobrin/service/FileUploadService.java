package com.theater.kobrin.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    void storePost(MultipartFile file, String fileName);
    void storeProfile(MultipartFile file, String fileName);
    void init();
    void deleteAllPost();
    void deleteAllProfile();
}
