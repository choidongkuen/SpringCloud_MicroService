package com.example.exception;

import lombok.Getter;

@Getter
public class NotFoundCatalogsException extends RuntimeException {
    public NotFoundCatalogsException(String message) {
        super(message);
    }
}
