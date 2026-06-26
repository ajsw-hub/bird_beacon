package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BIRDS")
@NoArgsConstructor

public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String name;
    private String sciname;
    private String family;
    private String birdorder;
    private String status;
    private Float lengthmin;
    private Float lengthmax;
    private Float wingspanmin;
    private Float wingspanmax;
    private String images;

}
