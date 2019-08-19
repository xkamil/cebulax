package com.canx.cebulax.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ServiceException {
    private static final String ERROR = "not_found";

    public EntityNotFoundException(String entity) {
        super(entity + " not found.", HttpStatus.BAD_REQUEST.value(), ERROR);
    }
}
