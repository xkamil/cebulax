package com.canx.cebulax.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final int code;
    private final String error;
    private final String message;

    public ServiceException(String message, int code, String error) {
        super();
        this.message = message;
        this.code = code;
        this.error = error;
    }
}
