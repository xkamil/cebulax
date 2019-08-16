package com.canx.cebulax.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("Bad credentials");
    }
}
