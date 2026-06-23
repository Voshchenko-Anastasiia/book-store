package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseProjectException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;
    private final HttpStatus httpStatus;

    protected BaseProjectException(String messageKey, Object[] args, HttpStatus httpStatus) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
        this.httpStatus = httpStatus;
    }

    public String getMessageKey() { return messageKey; }
    public Object[] getArgs() { return args; }
    public HttpStatus getHttpStatus() { return httpStatus; }

}
