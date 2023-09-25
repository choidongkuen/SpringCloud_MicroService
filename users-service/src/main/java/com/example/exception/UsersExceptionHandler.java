package com.example.exception;

import com.example.controller.UsersController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UsersController.class)
public class UsersExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundExceptionHandler(
            UsernameNotFoundException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception, HttpStatus.BAD_REQUEST));
    }
}
