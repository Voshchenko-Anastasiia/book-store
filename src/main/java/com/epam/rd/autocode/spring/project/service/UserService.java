package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    User registerNewUser(UserRegistrationDTO dto);
    void deleteUser(Long id);
    List<UserRegistrationDTO> findAllUsers();
    List<UserRegistrationDTO> findAllNonAdminUsers();
}
