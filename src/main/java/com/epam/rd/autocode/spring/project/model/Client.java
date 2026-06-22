package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

// requirement: Spring Data JPA - repositories & database mapping setup
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Client extends User {
    private BigDecimal balance;

    public Client(Long id, String email, String name, String password, BigDecimal balance, String role) {
        super(id, email, name, password, role);
        this.balance = balance;
    }
}