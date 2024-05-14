package com.theater.kobrin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "comment")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment extends AbstractEntity{
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Exhibit exhibit;
}
