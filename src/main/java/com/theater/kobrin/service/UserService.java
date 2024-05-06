package com.theater.kobrin.service;

import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.entity.User;

public interface UserService {
    User regist(UserRequest request);
    User findByUsername(String username);

}
