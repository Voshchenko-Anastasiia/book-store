package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BaseProjectException {
    public InsufficientBalanceException() {
        super("error.insufficient.balance", HttpStatus.BAD_REQUEST);
    }
}