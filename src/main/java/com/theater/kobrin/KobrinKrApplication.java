package com.theater.kobrin;

import com.theater.kobrin.service.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KobrinKrApplication {
    public static void main(String[] args) {
        SpringApplication.run(KobrinKrApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileUploadService fileUploadService) {
        return (args) -> {
            fileUploadService.init();
        };
    }
}
