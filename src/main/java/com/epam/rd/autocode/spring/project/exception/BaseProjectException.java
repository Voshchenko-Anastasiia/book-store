package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseProjectException extends RuntimeException {

    private final String messageKey;
    private final HttpStatus httpStatus;
    private final Object[] args;

    protected BaseProjectException(String messageKey, HttpStatus httpStatus, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public String getMessageKey() { return messageKey; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public Object[] getArgs() { return args; }
}