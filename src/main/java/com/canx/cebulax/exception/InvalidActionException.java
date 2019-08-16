package com.canx.cebulax.exception;

public class InvalidActionException extends RuntimeException {

    public InvalidActionException(String action, String reason) {
        super(String.format("Can't perform action %s - %s", action, reason));
    }
}
