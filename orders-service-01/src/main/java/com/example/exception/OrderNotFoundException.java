package com.example.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
