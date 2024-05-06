package com.theater.kobrin.service.impl;

import com.theater.kobrin.config.FileUploadProperties;
import com.theater.kobrin.exception.StorageException;
import com.theater.kobrin.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final Path rootLocationPost;
    private final Path rootLocationProfile;

    @Autowired
    public FileUploadServiceImpl(FileUploadProperties properties) {
        if (properties.getLocationPost().trim().isEmpty() || properties.getLocationProfile().trim().isEmpty() ) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocationPost = Paths.get(properties.getLocationPost());
        this.rootLocationProfile = Paths.get(properties.getLocationProfile());
    }

    @Override
    public void storePost(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocationPost.resolve(
                    Paths.get(fileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocationPost.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
    @Override
    public void storeProfile(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocationProfile.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocationProfile.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void deleteAllPost() {
        FileSystemUtils.deleteRecursively(rootLocationPost.toFile());
    }

    @Override
    public void deleteAllProfile() {
        FileSystemUtils.deleteRecursively(rootLocationProfile.toFile());
    }
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocationPost);
            Files.createDirectories(rootLocationProfile);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
