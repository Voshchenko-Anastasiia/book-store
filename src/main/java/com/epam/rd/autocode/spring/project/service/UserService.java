package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    // Core contract for finding a user by their unique login credential
    Optional<User> getUserByEmail(String email);
    User registerNewUser(UserRegistrationDTO dto);
}
