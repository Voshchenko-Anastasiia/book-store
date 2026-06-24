package com.epam.rd.autocode.spring.project.specification;

import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> hasName(String name) {
        return (root, query, cb) ->
                (name == null || name.isEmpty()) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) ->
                (author == null || author.isEmpty()) ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }
}