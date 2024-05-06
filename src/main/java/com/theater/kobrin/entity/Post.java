package com.theater.kobrin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends AbstractEntity{
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "text", nullable = false, length = 1000)
    private  String text;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
