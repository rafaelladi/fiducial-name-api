package com.dietrich.fiducial.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends Exception {

    private final HttpStatus status;

    public APIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public APIException(HttpStatus status, String message, Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

}
