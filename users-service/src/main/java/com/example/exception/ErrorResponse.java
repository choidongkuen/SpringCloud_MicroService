package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String errorSimpleName;
    private final String errorMessage;
    private final LocalDateTime timestamp;

    public ErrorResponse(Exception exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.errorMessage = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(Exception exception, HttpStatus httpStatus) {
        return new ErrorResponse(exception, httpStatus);
    }
}
