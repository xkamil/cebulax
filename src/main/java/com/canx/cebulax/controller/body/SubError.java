package com.canx.cebulax.controller.body;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
class SubError {
    private final String field;
    private final String message;
    private final String code;

    public static SubError from(FieldError fieldError) {
        return new SubError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getCode());
    }

    public SubError(String field, String message, String code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }
}
