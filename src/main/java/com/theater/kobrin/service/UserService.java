package com.theater.kobrin.service;

import com.theater.kobrin.dto.UserDto;
import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.entity.User;

import java.util.List;

public interface UserService {
    User regist(UserRequest request);
    User findByUsername(String username);

    List<UserDto> findAll();

    User deleteById(Long id);
}
