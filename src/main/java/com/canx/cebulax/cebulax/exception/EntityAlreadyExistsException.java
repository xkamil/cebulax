package com.canx.cebulax.cebulax.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String entity, String id) {
        super(String.format("%s %s already exists.", entity, id));
    }
}
