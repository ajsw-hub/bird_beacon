package com.makersacademy.acebook.controller.DTOs;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostView {

    private Long id;
    private String content;
    private Long posterId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long birdId;
    private String user_img;
    private String birdName;
    private String birdImage;
    private LocalDate dateOfSighting;
    private LocalDateTime createdAt;
    private boolean enabled;
    private boolean restricted;

    public PostView(
            Long id,
            String content,
            Long posterId,
            BigDecimal latitude,
            BigDecimal longitude,
            Long birdId,
            String user_img,
            String birdName,
            String birdImage,
            LocalDate dateOfSighting,
            LocalDateTime createdAt,
            boolean enabled,
            boolean restricted
            ) {

        this.id = id;
        this.content = content;
        this.posterId = posterId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.birdId = birdId;
        this.user_img = user_img;
        this.birdName = birdName;
        this.birdImage = birdImage;
        this.dateOfSighting = dateOfSighting;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.restricted = restricted;
    }
}