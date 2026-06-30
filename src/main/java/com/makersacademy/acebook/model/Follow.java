package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static java.lang.Boolean.TRUE;

@Entity
@Table(name = "follows")
@NoArgsConstructor
@Data

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long followerid;
    private Long followingid;

}