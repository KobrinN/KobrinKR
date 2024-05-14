package com.theater.kobrin.service.impl;

import com.theater.kobrin.dto.UserDto;
import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.entity.Role;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.RoleRepo;
import com.theater.kobrin.repo.UserRepo;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            Role role = roleRepo.findByName("ROLE_ADMIN").get();

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

    @Override
    public List<UserDto> findAll() {
        Role roleUser = roleRepo.findByName("ROLE_USER").get();
        return userRepo.findAll().stream()
                .filter(user -> user.getRoles().contains(roleUser))
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> userFromDb = userRepo.findById(id);
        if(userFromDb.isEmpty()) throw new NotFoundException("NOT FOUND USER!");
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN").get();
        if(!userFromDb.get().getRoles().contains(roleAdmin)){
            userRepo.deleteById(id);
            return userFromDb.get();
        }else{
            return null;
        }
    }

    protected UserDto buildDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
