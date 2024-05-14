package com.theater.kobrin.service.impl;

import com.theater.kobrin.dto.CommentDto;
import com.theater.kobrin.dto.CommentRequest;
import com.theater.kobrin.entity.Comment;
import com.theater.kobrin.entity.Exhibit;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.repo.CommentRepo;
import com.theater.kobrin.repo.ExhibitRepo;
import com.theater.kobrin.repo.UserRepo;
import com.theater.kobrin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final ExhibitRepo exhibitRepo;
    private final UserRepo userRepo;

    @Override
    public List<CommentDto> findByExhibitId(Long id) {
        Optional<Exhibit> exhibitFromDb = exhibitRepo.findById(id);
        if(exhibitFromDb.isEmpty()) throw new NotFoundException("NOT FOUND EXHIBIT!");

        List<CommentDto> commentDtos = exhibitFromDb.get().getComments().stream()
                .sorted((a, b)->{
                    if(a.getDate().isAfter(b.getDate())) return -1;
                    else if(a.getDate().equals(b.getDate())) return 0;
                    return 1;
                })
                .map(this::buildDto)
                .toList();
        if(commentDtos.isEmpty()) throw new NotFoundException("NOT FOUND COMMENTS!");

        return  commentDtos;
    }

    @Override
    public Comment add(Long exhibitId, CommentRequest commentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userFromDb = userRepo.findByUsername(auth.getName());
        Optional<Exhibit> exhibitFromDb = exhibitRepo.findById(exhibitId);
        if(exhibitFromDb.isEmpty()) throw new NotFoundException("NOT FOUND EXHIBIT!");

        Comment comment = new Comment();
        comment.setDate(LocalDateTime.now());
        comment.setText(commentRequest.getComment());
        comment.setUser(userFromDb.get());
        comment.setExhibit(exhibitFromDb.get());

        commentRepo.saveAndFlush(comment);
        return comment;
    }

    @Override
    public Comment delete(Long id) {
        Optional<Comment> commentFromDb = commentRepo.findById(id);
        if(commentFromDb.isEmpty()) throw new NotFoundException("NOT FOUND COMMENT!");
        commentRepo.delete(commentFromDb.get());
        return commentFromDb.get();
    }

    protected CommentDto buildDto(Comment comment){
        return CommentDto.builder()
                .userId(comment.getUser().getId())
                .id(comment.getId())
                .text(comment.getText())
                .date(comment.getDate().toString())
                .username(comment.getUser().getUsername())
                .build();
    }
}
