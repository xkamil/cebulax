package com.canx.cebulax.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String id) {
        super(String.format("%s %s not found.", entity, id));
    }
}
