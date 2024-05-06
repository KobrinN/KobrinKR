package com.theater.kobrin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "public", name = "role")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Role extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
