package com.dietrich.fiducial.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends APIException {

    public NotFoundException(String type, String field, Object id) {
        super(HttpStatus.NOT_FOUND,
                String.format("No entity of type: %s found with %s: %s", type, field, id.toString()));
    }
}
