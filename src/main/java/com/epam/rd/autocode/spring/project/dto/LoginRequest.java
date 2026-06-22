package com.epam.rd.autocode.spring.project.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}