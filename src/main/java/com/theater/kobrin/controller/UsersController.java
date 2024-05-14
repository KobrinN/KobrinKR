package com.theater.kobrin.controller;

import com.theater.kobrin.dto.UserDto;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UsersController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAll(Model model){
        List<UserDto> userDtos = userService.findAll();
        if(userDtos.isEmpty()) return "user_all_not_found";
        model.addAttribute("users", userDtos);
        return "user_all";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@RequestParam Long id){
        userService.deleteById(id);
        return "redirect:/api/v1/user/all";
    }
}
