package com.ll.hype.global.exception.advice;

import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.exception.dto.ErrorResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HypeControllerAdvice {
    private static final String ERROR_PAGE = "global/error/error_page";

    @ExceptionHandler(IllegalArgumentException.class)
    public String exceptionMain(IllegalArgumentException e, Model model) {
        ErrorResponse errorResponse = ErrorResponse.of(404L, e);
        model.addAttribute("data", errorResponse);
        return ERROR_PAGE;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(EntityNotFoundException e, Model model) {
        ErrorResponse errorResponse = ErrorResponse.of(404L, e);
        model.addAttribute("data", errorResponse);
        return ERROR_PAGE;
    }
}
