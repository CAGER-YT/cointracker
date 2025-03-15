package com.rooter.cointracker.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "username", "password", "email", "role"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String email;
    private String role;
}
