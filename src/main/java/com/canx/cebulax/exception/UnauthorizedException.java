package com.canx.cebulax.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ServiceException {
    private static final String ERROR = "unauthorized";

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED.value(), ERROR);
    }
}
