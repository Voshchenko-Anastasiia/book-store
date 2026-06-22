package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// requirement: Spring Data JPA - repositories setup
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // requirement: Spring Data JPA - implementation of custom queries
    // requirement: Nice to Have - Searching, Pagination & Sorting
    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // requirement: Spring Data JPA - implementation of custom queries
    // requirement: Nice to Have - Searching, Pagination & Sorting by Genre
    Page<Book> findByGenreIgnoreCase(String genre, Pageable pageable);
}
