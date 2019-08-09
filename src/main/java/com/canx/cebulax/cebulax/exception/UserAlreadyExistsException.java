package com.canx.cebulax.cebulax.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String userName) {
        super(String.format("User with name %s already exists.", userName));
    }
}
