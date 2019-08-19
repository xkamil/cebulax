package com.canx.cebulax.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends ServiceException {
    private static final String ERROR = "invalid_argument";

    public InvalidArgumentException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value(), ERROR);
    }
}
