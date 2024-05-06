package com.theater.kobrin.controller;

import com.theater.kobrin.dto.UserRequest;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(UserRequest request){
        return "redirect:/api/v1/post/all";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("message", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(UserRequest request, Model model){
        try {
            userService.regist(request);
        }catch (NotValidFieldException exception){
            if (exception.getMessage().equals("NO FIELDS!")) {
                model.addAttribute("message", "Введите поля!");
            }else if (exception.getMessage().equals("ALREADY EXIST!")){
                model.addAttribute("message", "Введите другой USERNAME!");
            }
            return "registration";
        }
        return "redirect:/api/v1/login";
    }
}
