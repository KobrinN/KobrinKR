package com.theater.kobrin.service.impl;

import com.theater.kobrin.dto.PostDto;
import com.theater.kobrin.dto.PostRequest;
import com.theater.kobrin.entity.Post;
import com.theater.kobrin.entity.User;
import com.theater.kobrin.exception.NotFoundException;
import com.theater.kobrin.exception.NotValidFieldException;
import com.theater.kobrin.repo.PostRepo;
import com.theater.kobrin.repo.UserRepo;
import com.theater.kobrin.service.FileUploadService;
import com.theater.kobrin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final FileUploadService fileUploadService;

    @Override
    public Post add(PostRequest request, String username) {

        if (!StringUtils.hasText(request.getName()) ||
                !StringUtils.hasText(request.getDescription()) ||
                !StringUtils.hasText(request.getText())) throw new NotValidFieldException("EMPTY FIELDS!");

        Post post = new Post();
        post.setName(request.getName());
        post.setDescription(request.getDescription());
        post.setText(request.getText());
        //post.setImage("");
        Optional<User> userFromDb = userRepo.findByUsername(username);
        if (userFromDb.isEmpty()) throw new NotFoundException("NOT FOUND USER WITH USERNAME = " + username + "!");
        post.setUser(userFromDb.get());
        postRepo.saveAndFlush(post);

        //String nameOfImage = "post" + post.getId() + ".jpg";
        ///fileUploadService.storePost(request.getImage(), nameOfImage);
        //post.setImage(nameOfImage);

        postRepo.saveAndFlush(post);
        return post;
    }

    @Override
    public List<PostDto> getAll() {
        return postRepo.findAll().stream()
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getById(Long id) {
        Optional<Post> postFromBd = postRepo.findById(id);
        if(postFromBd.isEmpty()) throw new NotFoundException("NOT FOUND POST WITH ID = " + id + "!");
        return buildDto(postFromBd.get());
    }

    public PostDto buildDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .name(post.getName())
                //.image("/images/"+post.getImage())
                .description(post.getDescription())
                .text(post.getText())
                .build();
    }
}
