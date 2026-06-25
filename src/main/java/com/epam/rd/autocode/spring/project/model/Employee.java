package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Employee extends User {
    private String phone;
    private LocalDate birthDate;

    public Employee(Long id, String email, String name, String password, LocalDate birthDate, String phone, String role, BigDecimal balance) {
        super(id, email, name, password, role, balance);
        this.birthDate = birthDate;
        this.phone = phone;
    }
}