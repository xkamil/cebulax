package com.canx.cebulax.exception;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String argument, String reason) {
        super(String.format("Invalid argument: %s - %s", argument, reason));
    }
}
