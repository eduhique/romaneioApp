package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.dto.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RomaneioAppControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
