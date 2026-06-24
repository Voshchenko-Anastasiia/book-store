package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.exception.ResourceNotFoundException;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import com.epam.rd.autocode.spring.project.specification.BookSpecifications;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);

        if (book == null) {
            throw new ResourceNotFoundException("Book with registration code " + id + " does not exist.");
        }

        BookDTO bookDto = new BookDTO();

        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setGenre(book.getGenre());
        bookDto.setAgeGroup(book.getAgeGroup());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setPages(book.getPages());
        bookDto.setPublicationDate(book.getPublicationDate());

        model.addAttribute("book", bookDto);
        return "book-details";
    }

    @GetMapping("/books/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public String showAddForm(Model model) {
        // requirement: Provide an empty BookDTO container for Thymeleaf form binding
        model.addAttribute("bookDto", new BookDTO());
        return "add-book";
    }

    @PostMapping("/books/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public String addBook(@Valid @ModelAttribute BookDTO bookDto, BindingResult result) {
        if (result.hasErrors()) {
            return "book-form";
        }
        bookService.save(bookDto);
        return "redirect:/books";
    }

    @PostMapping("/books/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,

            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "author", required = false) String author,

            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Book> spec = Specification.where(BookSpecifications.hasName(name))
                .and(BookSpecifications.hasAuthor(author));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        model.addAttribute("bookPage", bookPage);

        model.addAttribute("currentName", name);
        model.addAttribute("currentAuthor", author);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        return "books";
    }

    @GetMapping("/books/lucky")
    public String getLuckyBook() {
        return bookRepository.findRandomBook()
                .map(book -> "redirect:/books/" + book.getId())
                .orElse("redirect:/books");
    }
}