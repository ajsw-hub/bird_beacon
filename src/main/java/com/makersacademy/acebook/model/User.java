package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.TRUE;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private Boolean enabled;

    public User(String usenname) {
        this.username = username;
        this.enabled = TRUE;

    }
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.enabled = TRUE;

    }
}
