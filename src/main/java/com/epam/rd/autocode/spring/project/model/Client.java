package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "clients")
public class Client extends User {

    private BigDecimal balance;

    public Client(Long id, String email, String name, String password, BigDecimal balance) {
        this.setId(id);
        this.setEmail(email);
        this.setName(name);
        this.setPassword(password);
        this.balance = balance;
    }
}
