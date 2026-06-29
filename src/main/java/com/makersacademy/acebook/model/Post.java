package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="POSTS")
@Getter
@Setter
@NoArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String content;
    @Column(name = "posterid")
    private long posterId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column(name = "bird_id")
    private Long birdId;
    private String user_img;

}
