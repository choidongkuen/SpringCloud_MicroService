package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatalogExceptionHandler {

    @ExceptionHandler(NotFoundCatalogsException.class)
    public ResponseEntity<ErrorResponse> of(NotFoundCatalogsException exception) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception, HttpStatus.BAD_REQUEST));
    }
}
