package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAllBooks();

    Book findById(Long id);

    BookDTO getBookByName(String name);

    BookDTO updateBookByName(String name, BookDTO book);

    void deleteBookByName(String name);

    BookDTO addBook(BookDTO book);

    Page<BookDTO> getAllBooksPaginated(Pageable pageable);

    Page<BookDTO> searchBooksByNamePaginated(String name, Pageable pageable);

    void save(BookDTO bookDto);
    void deleteById(Long id);

    Page<Book> findAll(Pageable pageable);

}