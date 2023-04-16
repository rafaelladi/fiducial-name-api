package com.dietrich.fiducial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception, WebRequest webRequest) {
        return ResponseEntity
                .status(exception instanceof APIException apiException ? apiException.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDetails(exception.getMessage(), webRequest.getDescription(false)));
    }

}
