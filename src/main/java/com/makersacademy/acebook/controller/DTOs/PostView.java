package com.makersacademy.acebook.controller.DTOs;

import lombok.Getter;

import java.math.BigDecimal;

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

    public PostView(
            Long id,
            String content,
            Long posterId,
            BigDecimal latitude,
            BigDecimal longitude,
            Long birdId,
            String user_img,
            String birdName) {

        this.id = id;
        this.content = content;
        this.posterId = posterId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.birdId = birdId;
        this.user_img = user_img;
        this.birdName = birdName;
    }
}