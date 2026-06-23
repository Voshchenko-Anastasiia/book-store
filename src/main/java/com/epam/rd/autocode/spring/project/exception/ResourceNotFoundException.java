package com.epam.rd.autocode.spring.project.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseProjectException {
    public ResourceNotFoundException(String resourceName) {
        super("error.resource.not.found", new Object[]{resourceName}, HttpStatus.NOT_FOUND);
    }
}
