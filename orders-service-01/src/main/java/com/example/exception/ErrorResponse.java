package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String errorSimpleName;
    private String errorMsg;
    private LocalDateTime timestamp;

    public ErrorResponse(Exception exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.errorMsg = exception.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(Exception exception, HttpStatus httpStatus) {
        return new ErrorResponse(exception,httpStatus);
    }
}
