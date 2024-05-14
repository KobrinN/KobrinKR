package com.theater.kobrin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "public",name = "exhibit")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exhibit extends AbstractEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image", nullable = false)
    private String image;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exhibit")
    private List<Post> posts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "exhibit")
    private List<Comment> comments;
}
