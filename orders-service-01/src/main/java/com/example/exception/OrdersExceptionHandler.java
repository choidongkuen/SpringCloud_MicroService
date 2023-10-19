package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrdersExceptionHandler {

    /* 일치하는 회원 존재 x */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(
            UserNotFoundException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception, HttpStatus.BAD_REQUEST));
    }

    /* 일치하는 상품 존재 x */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundExceptionHandler(
            ProductNotFoundException exception
    ) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(exception, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /* 일치하는 주문 존재 x */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> orderNotFoundExceptionHandler(
            OrderNotFoundException exception
    ) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(exception, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
