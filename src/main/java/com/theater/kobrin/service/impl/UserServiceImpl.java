package com.theater.kobrin.service.impl;

import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.entity.Role;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.RoleRepo;
import com.theater.kobrin.repo.UserRepo;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User regist(UserRequest request) {
        Optional<User> userFromDb = userRepo.findByUsername(request.getUsername());
        if (userFromDb.isEmpty()) {
            if (request.getUsername().isEmpty() ||
                    request.getPassword().isEmpty()) throw new NotValidFieldException("NO FIELDS!");

            User user = new User();
            Role role = roleRepo.findByName("ROLE_USER").get();

            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Set.of(role));
            userRepo.saveAndFlush(user);
            return user;
        } else {
            throw new NotValidFieldException("ALREADY EXIST!");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }
}
