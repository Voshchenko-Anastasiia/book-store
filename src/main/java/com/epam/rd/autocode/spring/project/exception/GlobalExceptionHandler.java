package com.epam.rd.autocode.spring.project.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex,
                                         Model model,
                                         HttpServletResponse response) {

        logger.error("Resource not found: {}", ex.getMessage());

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 404);
        return "error";
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public String handleInsufficientBalance(InsufficientBalanceException ex,
                                            Model model,
                                            HttpServletResponse response) {

        logger.warn("Insufficient balance: {}", ex.getMessage()); // Use warn for business errors

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 400);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex,
                                         Model model,
                                         HttpServletResponse response) {

        logger.error("Unexpected error occurred: ", ex);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        model.addAttribute("errorMessage", "An unexpected internal error occurred.");
        model.addAttribute("status", 500);
        return "error";
    }
}