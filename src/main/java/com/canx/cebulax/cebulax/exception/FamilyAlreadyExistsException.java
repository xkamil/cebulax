package com.canx.cebulax.cebulax.exception;

public class FamilyAlreadyExistsException extends RuntimeException {

    public FamilyAlreadyExistsException(String familyName) {
        super(String.format("Family with name %s already exists.", familyName));
    }
}
