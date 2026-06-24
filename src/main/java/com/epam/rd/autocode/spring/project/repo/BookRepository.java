package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// requirement: Spring Data JPA - repositories setup
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // requirement: Spring Data JPA - implementation of custom queries
    // requirement: Nice to Have - Searching, Pagination & Sorting by Genre
    @Query(value = "SELECT * FROM books ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Book> findRandomBook();}
