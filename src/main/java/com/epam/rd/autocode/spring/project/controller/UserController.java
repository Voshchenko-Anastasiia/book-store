package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@RequestBody UserRegistrationDTO dto) {
        // ... logic to save the new employee
        //TODO
        return ResponseEntity.ok("Employee created successfully");
    }
}