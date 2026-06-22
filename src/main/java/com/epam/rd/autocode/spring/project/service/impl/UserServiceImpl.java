package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import com.epam.rd.autocode.spring.project.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

// requirement: Spring Security - authentication strategies (Database-backed Authentication)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // requirement: Spring Data JPA - implementation of custom queries through service contracts
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerNewUser(UserRegistrationDTO dto) {
        User user;

        String cleanRole = dto.getRole().toUpperCase().replace("ROLE_", "");

        if ("EMPLOYEE".equals(cleanRole) || "ADMIN".equals(cleanRole)) {
            user = new Employee();
        } else {
            user = new Client();
            ((Client) user).setBalance(BigDecimal.ZERO);
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(cleanRole); // Saves exactly "ADMIN", "EMPLOYEE", or "CLIENT"

        return userRepository.save(user);
    }
}
