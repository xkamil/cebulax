package com.canx.cebulax.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ServiceException {
    private static final String ERROR = "conflict";

    public EntityAlreadyExistsException(String entity) {
        super(entity + " already exists.", HttpStatus.CONFLICT.value(), ERROR);
    }
}
