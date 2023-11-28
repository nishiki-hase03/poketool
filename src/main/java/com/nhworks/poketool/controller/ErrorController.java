package com.nhworks.poketool.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler({Exception.class})
    public String exceptionHandle(Exception e, Model model) {
        return "error";
    }
}
