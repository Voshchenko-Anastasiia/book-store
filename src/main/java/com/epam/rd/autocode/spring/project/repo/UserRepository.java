package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// requirement: Spring Data JPA - Initialize UserRepository layer bound to database context
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // requirement: Spring Data JPA - Custom derived query method for email lookups
    Optional<User> findByEmail(String email);
}