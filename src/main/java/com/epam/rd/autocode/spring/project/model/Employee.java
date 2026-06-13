package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "employees")
public class Employee extends User {

    private String phone;
    private LocalDate birthDate;

    public Employee(Long id, String email, String name, String password, LocalDate birthDate, String phone) {
        super(id, email, name, password);
        this.birthDate = birthDate;
        this.phone = phone;
    }
}
