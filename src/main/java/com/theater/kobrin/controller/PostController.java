package com.theater.kobrin.controller;

import com.theater.kobrin.dto.PostRequest;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("message", "");
        model.addAttribute("Post", new PostRequest());
        return "post_adding";
    }

    @PostMapping("/add")
    public String add(PostRequest request, Model model){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
                String currentUserName = auth.getName();
                postService.add(request, currentUserName);
            }
        }catch (NotValidFieldException exception){
            if (exception.getMessage().equals("EMPTY FIELDS!")) {
                model.addAttribute("message", "Введите поля!");
            }
            return "redirect:/api/v1/post/all";
        }
        return "redirect:/api/v1/post/add";
    }

    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("posts", postService.getAll());
        return "posts";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.getById(id));
        return "post";
    }
}
