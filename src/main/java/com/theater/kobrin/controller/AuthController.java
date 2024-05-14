package com.theater.kobrin.controller;

import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.entity.Role;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.RoleRepo;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleRepo roleRepo;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/api/v1/home";
    }

    @GetMapping("/home")
    public String getHome() {
        Optional<Role> roleAdmin = roleRepo.findByName("ROLE_ADMIN");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
            User user = userService.findByUsername(auth.getName());
            if (user.getRoles().contains(roleAdmin.get())) return "home_admin";
        }
        return "home";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("message", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(UserRequest request, Model model) {
        try {
            userService.regist(request);
        } catch (NotValidFieldException exception) {
            if (exception.getMessage().equals("NO FIELDS!")) {
                model.addAttribute("message", "Введите поля!");
            } else if (exception.getMessage().equals("ALREADY EXIST!")) {
                model.addAttribute("message", "Введите другой USERNAME!");
            }
            return "registration";
        }
        return "redirect:/api/v1/login";
    }
}
