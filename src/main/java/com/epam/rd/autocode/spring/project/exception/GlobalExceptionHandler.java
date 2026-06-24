package com.epam.rd.autocode.spring.project.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseProjectException.class)
    public String handleBusinessExceptions(BaseProjectException ex,
                                           Locale locale,
                                           Model model,
                                           HttpServletResponse response) {

        String localizedMessage = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), locale);

        logger.warn("Business rule violation [{}]: {}", ex.getHttpStatus(), localizedMessage);

        response.setStatus(ex.getHttpStatus().value());
        model.addAttribute("errorMessage", localizedMessage);
        model.addAttribute("status", ex.getHttpStatus().value());

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex,
                                         Locale locale,
                                         Model model,
                                         HttpServletResponse response) {

        logger.error("Unexpected error occurred: ", ex);

        String localizedMessage = messageSource.getMessage("error.unexpected", null, locale);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        model.addAttribute("errorMessage", localizedMessage);
        model.addAttribute("status", 500);

        return "error";
    }
}