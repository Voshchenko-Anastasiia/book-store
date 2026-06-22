package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// requirement: Spring Data JPA - implementation of custom queries through service contracts
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookDTO> getAllBooksPaginated(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Page<BookDTO> searchBooksByNamePaginated(String name, Pageable pageable) {

        Page<Book> bookEntityPage = bookRepository.findByNameContainingIgnoreCase(name, pageable);

        return bookEntityPage.map(this::convertToDTO);
    }

    @Override
    public void deleteBookByName(String name) {
        Book existingBook = bookRepository.findByNameContainingIgnoreCase(name, Pageable.unpaged())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot delete. Book not found with name: " + name));
        bookRepository.delete(existingBook);
    }

    @Override
    public BookDTO getBookByName(String name) {
        return bookRepository.findByNameContainingIgnoreCase(name, Pageable.unpaged())
                .stream()
                .findFirst()
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Book not found with name: " + name));
    }

    @Override
    public BookDTO updateBookByName(String name, BookDTO bookDto) {

        Book existingBook = bookRepository.findByNameContainingIgnoreCase(name, Pageable.unpaged())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found to update: " + name));

        existingBook.setName(bookDto.getName());
        existingBook.setGenre(bookDto.getGenre());
        existingBook.setAgeGroup(bookDto.getAgeGroup());
        existingBook.setPrice(bookDto.getPrice());
        existingBook.setPublicationDate(bookDto.getPublicationDate());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPages(bookDto.getPages());
        existingBook.setCharacteristics(bookDto.getCharacteristics());
        existingBook.setDescription(bookDto.getDescription());
        existingBook.setLanguage(bookDto.getLanguage());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public BookDTO addBook(BookDTO bookDto) {
        Book bookEntity = convertToEntity(bookDto);
        Book savedBook = bookRepository.save(bookEntity);
        return convertToDTO(savedBook);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // requirement: DTOs mapping - conversion of Entity models to Transfer Layers
    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getGenre(),
                book.getAgeGroup(),
                book.getPrice(),
                book.getPublicationDate(),
                book.getAuthor(),
                book.getPages(),
                book.getCharacteristics(),
                book.getDescription(),
                book.getLanguage()
        );
    }

    // requirement: DTOs mapping - conversion of incoming UI data back to persistent structural database classes
    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setGenre(dto.getGenre());
        book.setAgeGroup(dto.getAgeGroup());
        book.setPrice(dto.getPrice());
        book.setPublicationDate(dto.getPublicationDate());
        book.setAuthor(dto.getAuthor());
        book.setPages(dto.getPages());
        book.setCharacteristics(dto.getCharacteristics());
        book.setDescription(dto.getDescription());
        book.setLanguage(dto.getLanguage());
        return book;
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void save(BookDTO bookDto) {

        Book book = new Book();
        book.setName(bookDto.getName());
        book.setGenre(bookDto.getGenre());
        book.setAgeGroup(bookDto.getAgeGroup());
        book.setPrice(bookDto.getPrice());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setAuthor(bookDto.getAuthor());
        book.setPages(bookDto.getPages());
        book.setCharacteristics(bookDto.getCharacteristics());
        book.setDescription(bookDto.getDescription());
        book.setLanguage(bookDto.getLanguage());

        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
