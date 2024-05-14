package com.theater.kobrin.controller;

import com.theater.kobrin.dto.CommentRequest;
import com.theater.kobrin.entity.Comment;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addComment(@RequestParam Long id, String comment){
        if(comment.isEmpty()) return "redirect:/api/v1/exhibit/"+id;
        try{
            CommentRequest request = new CommentRequest(comment);
            commentService.add(id, request);
        }catch (NotFoundException exception){
        }
        return "redirect:/api/v1/exhibit/"+id;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteComment(@RequestParam Long id){
        Comment comment = new Comment();
        try{
            comment = commentService.delete(id);
        }catch (NotFoundException exception){
        }
        return "redirect:/api/v1/exhibit/"+comment.getExhibit().getId();
    }
}
