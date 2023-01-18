package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.dto.ApiErrors;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RomaneioAppControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> erros =
                ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .collect(
                                Collectors.toList());
        return new ApiErrors(erros);
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class, PropertyReferenceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadRequestRuntimeException(RuntimeException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadRequestMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ApiErrors(ex.getAllErrors().stream()
                                     .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                     .collect(Collectors.toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
