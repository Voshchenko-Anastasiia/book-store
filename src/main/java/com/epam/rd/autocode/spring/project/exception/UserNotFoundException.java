package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseProjectException {

    public UserNotFoundException(String email) {
        super("error.user.notfound", HttpStatus.NOT_FOUND, email);
    }
}