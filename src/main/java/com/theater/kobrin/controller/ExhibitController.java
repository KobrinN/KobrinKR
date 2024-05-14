package com.theater.kobrin.controller;

import com.theater.kobrin.dto.*;
import com.theater.kobrin.entity.Comment;
import com.theater.kobrin.entity.Exhibit;
import com.theater.kobrin.entity.Role;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.RoleRepo;
import com.theater.kobrin.repo.UserRepo;
import com.theater.kobrin.service.CommentService;
import com.theater.kobrin.service.ExhibitService;
import com.theater.kobrin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/exhibit")
@RequiredArgsConstructor
public class ExhibitController {
    private final ExhibitService exhibitService;
    private final CommentService commentService;
    private final UserService userService;
    private final RoleRepo roleRepo;

    private static String messageAdd = "";
    private static String messageEdit = "";

    @GetMapping("/all")
    public String getAll(Model model){
        Optional<Role> roleAdmin = roleRepo.findByName("ROLE_ADMIN");
        List<ExhibitDto> exhibits = exhibitService.findAll();
        model.addAttribute("exhibits", exhibits);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
            User user = userService.findByUsername(auth.getName());
            if (user.getRoles().contains(roleAdmin.get())) {
                if(exhibits.isEmpty()) return "exhibit_all_admin_empty";
                return "exhibit_all_admin";
            }
        }
        if(exhibits.isEmpty()) return "exhibit_all_empty";
        return "exhibit_all";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Role> roleAdmin = roleRepo.findByName("ROLE_ADMIN");
        try {
            ExhibitDto exhibitDto = exhibitService.findById(id);
            model.addAttribute("exhibit", exhibitDto);
            List<CommentDto> commentDtos = commentService.findByExhibitId(id);
            model.addAttribute("comments", commentDtos);
            model.addAttribute("Comment", new CommentRequest());
            if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
                User user = userService.findByUsername(auth.getName());
                if (user.getRoles().contains(roleAdmin.get())) {
                    return "exhibit_admin";
                }
            }
            return "exhibit";
        }catch (NotFoundException exception){
            if(exception.getMessage().equals("NOT FOUND EXHIBIT!")) {
                if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
                    User user = userService.findByUsername(auth.getName());
                    if (user.getRoles().contains(roleAdmin.get())) {
                        return "exhibit_not_found_admin";
                    }
                }
                return "exhibit_not_found";
            }
            if(exception.getMessage().equals("NOT FOUND COMMENTS!")){
                if (!(auth instanceof AnonymousAuthenticationToken) && !auth.getName().isEmpty()) {
                    User user = userService.findByUsername(auth.getName());
                    if (user.getRoles().contains(roleAdmin.get())) {
                        return "exhibit_withow_comments_admin";
                    }
                }
                return "exhibit_withow_comments";
            }
        }
        return "exhibit";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddPage(Model model){
        model.addAttribute("message", messageAdd);
        model.addAttribute("Exhibit", new ExhibitRequest());
        return "exhibit_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addExhibit(ExhibitRequest request, Model model){
        try {
                exhibitService.add(request);
        }catch(NotValidFieldException exception){
            if (exception.getMessage().equals("EMPTY FIELDS!")) {
                messageAdd = "Введите все поля!";
            }
            return "redirect:/api/v1/exhibit/add";
        }
        messageAdd = "";
        return "redirect:/api/v1/exhibit/add";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteExhibit(@RequestParam Long id){
        try{
            exhibitService.deleteById(id);
        }catch(NotFoundException exception){
            System.out.println(exception.getMessage());
        }
        return "redirect:/api/v1/exhibit/all";
    }
    @GetMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditPage(@RequestParam Long id, Model model){
        model.addAttribute("message", messageEdit);
        ExhibitDto exhibitDto = new ExhibitDto();
        try{
             exhibitDto = exhibitService.findById(id);
        }catch (NotFoundException exception){
            return "exhibit_not_found";
        }
        model.addAttribute("Exhibit", exhibitDto);
        return "exhibit_edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editExhibit(@RequestParam Long id, ExhibitRequest request, Model model){
        try {
            exhibitService.edit(request, id);
        }catch(NotValidFieldException exception){
            if (exception.getMessage().equals("EMPTY FIELDS!")) {
                messageEdit = "Введите поля!";
            }
            return "redirect:/api/v1/exhibit/edit?id="+id;
        }
        messageEdit = "";
        return "redirect:/api/v1/exhibit/edit?id="+id;
    }

}
