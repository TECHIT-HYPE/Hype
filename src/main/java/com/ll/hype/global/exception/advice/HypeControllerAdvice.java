package com.ll.hype.global.exception.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HypeControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public String exceptionMain(IllegalArgumentException e) {
        return "redirect:/";
    }
}
