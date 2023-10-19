package com.example.exception;

public class ProductStockNotEnoughException extends RuntimeException {
    public ProductStockNotEnoughException(String message) {
        super(message);
    }
}
