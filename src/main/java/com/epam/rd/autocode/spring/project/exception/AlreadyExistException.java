package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends BaseProjectException {
    public AlreadyExistException() {
        super("error.already.exist", HttpStatus.CONFLICT);
    }
}