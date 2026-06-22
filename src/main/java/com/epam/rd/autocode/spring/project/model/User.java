package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// requirement: Spring Data JPA - repositories & database mapping setup
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class User { //to prevent creating User type

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public User(Long id, String email, String name, String password, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}