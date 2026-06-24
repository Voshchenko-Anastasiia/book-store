package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import com.epam.rd.autocode.spring.project.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// requirement: Spring Security - authentication strategies (Database-backed Authentication)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    // requirement: Spring Data JPA - implementation of custom queries through service contracts
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerNewUser(UserRegistrationDTO dto) {
        if (getUserByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

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
        user.setRole(cleanRole);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orderRepository.deleteAll(orders);
        userRepository.deleteById(userId);
    }

    public List<UserRegistrationDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<UserRegistrationDTO> findAllNonAdminUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .filter(user -> !"ADMIN".equals(user.getRole())) // Filter out the admins
                .collect(Collectors.toList());
    }

    private UserRegistrationDTO mapToDTO(User user) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPassword("********");
        return dto;
    }
}
