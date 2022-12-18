package com.application.spring.controller;

import com.application.spring.exception.NotFoundException;
import com.application.spring.exception.ValidationException;
import com.application.spring.model.entity.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse<String>> catchNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse<String> response = ExceptionResponse.<String>builder()
                .type(ExceptionResponse.Type.ERROR)
                .description(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse<String>> catchNotFoundException(ValidationException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse<String> response = ExceptionResponse.<String>builder()
                .type(ExceptionResponse.Type.ERROR)
                .description(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
