package com.dietrich.fiducial.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private final String message;
    private final String details;
    private final Date timestamp = new Date();
}
