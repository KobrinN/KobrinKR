package com.theater.kobrin.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@Builder
public class UserRequest {
        private String username;
        private String password;
}
