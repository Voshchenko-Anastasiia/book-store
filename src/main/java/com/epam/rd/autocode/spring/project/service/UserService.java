package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User registerNewUser(UserRegistrationDTO dto);
    void deleteUser(Long id);
    List<UserRegistrationDTO> findAllUsers();
    List<UserRegistrationDTO> findAllNonAdminUsers();
    void topUpBalance(String email, BigDecimal amount);
    User reloadUser(Long id);
}