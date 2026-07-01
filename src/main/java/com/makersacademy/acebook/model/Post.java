package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="POSTS")
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
    @Column(name = "date_of_sighting")
    private LocalDate dateOfSighting;
    private LocalDateTime createdAt;
    private boolean enabled;
    private boolean restricted;

    public void rep() {

    }
}
